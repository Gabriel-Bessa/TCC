package br.com.bessatech.notify.listener;

import br.com.bessatech.notify.core.entity.Notify;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotifyListener {

    @EventListener
    public void triggerNotify(RedisKeyExpiredEvent<Notify> expiredNotify) {
        String keyspace = expiredNotify.getKeyspace();
        log.info("TRigger: {}", keyspace);
        if("notify-trigger".equals(keyspace)) {
            Notify notify = (Notify) expiredNotify.getValue();
            if (notify != null) {
                log.info("[NotifyListener::triggerNotify] triggered notify at time: {}", LocalDateTime.now());
                System.out.println(notify.getId());
            }
        }
    }

}
