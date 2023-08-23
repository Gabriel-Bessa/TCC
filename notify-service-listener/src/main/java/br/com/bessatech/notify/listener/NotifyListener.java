package br.com.bessatech.notify.listener;

import br.com.bessatech.notify.core.mongo.document.MArticleDocument;
import br.com.bessatech.notify.core.mongo.repository.MArticleRepository;
import br.com.bessatech.notify.core.redis.entity.Notify;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class NotifyListener {

    private final MArticleRepository articleRepository;
    private static final String targetKeyspace = "notify-trigger";

    @EventListener
    public void triggerNotify(RedisKeyExpiredEvent<Notify> expiredNotify) {
        String keyspace = expiredNotify.getKeyspace();
        if (targetKeyspace.equals(keyspace)) {
            String[] keys = new String(expiredNotify.getSource()).split(":");
            if (keys.length > 1) {
                log.info("[NotifyListener::triggerNotify] triggered notify at time: {} of notification id: {}", LocalDateTime.now(), keys[1]);
            }
        }
    }

    @AfterReturning(value = "execution(* br.com.bessatech.notify.core.redis.repository.NotifyRepository.save(..))", returning = "result")
    public void syncElasticPurchase(JoinPoint jp, Object result) {
        log.info("Saved notify in redis");
        MArticleDocument document = new MArticleDocument();
        document.setTitle("Debug");
        document.setStartDate(LocalDateTime.now());
        articleRepository.save(document);
    }

}
