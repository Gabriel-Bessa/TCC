package br.com.bessatech.notify.listener;

import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.mongo.document.MTriggerNotification;
import br.com.bessatech.notify.core.mongo.repository.MTriggerNotifyRepository;
import br.com.bessatech.notify.core.redis.entity.Notification;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class NotifyListener {
    private static final String targetKeyspace = "notification-trigger";
    private final MTriggerNotifyRepository mTriggerNotifyRepository;

    @EventListener
    public void triggerNotify(RedisKeyExpiredEvent<Notification> expiredNotify) {
        String keyspace = expiredNotify.getKeyspace();
        if (targetKeyspace.equals(keyspace)) {
            String[] keys = new String(expiredNotify.getSource()).split(":");
            if (keys.length > 1) {
                log.info("[NotifyListener::triggerNotify] triggered notify at time: {} of notification id: {}", LocalDateTime.now(), keys[1]);
            }
        }
    }

    @AfterReturning(value = "execution(* br.com.bessatech.notify.core.redis.repository.NotifyRepository.save(..))", returning = "result")
    public void syncElasticPurchase(Object result) {
        log.info("Double save in mongo database");
        Notification redisNotification = (Notification) result;
        saveRedisNotificationInMongo(redisNotification);
    }

    private void saveRedisNotificationInMongo(Notification redisNotification) {
        MTriggerNotification mongoNotification = new MTriggerNotification();
        mongoNotification.setText(redisNotification.getText());
        mongoNotification.setTypes(redisNotification.getTypes());
        mongoNotification.setId(redisNotification.getId());
        mongoNotification.setExpirationDate(redisNotification.getExpirationDate());
        mongoNotification.setStatus(NotificationStatus.WAITING_TRIGGER);
        mTriggerNotifyRepository.save(mongoNotification);
    }

}
