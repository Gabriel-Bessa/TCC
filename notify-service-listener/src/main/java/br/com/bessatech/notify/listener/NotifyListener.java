package br.com.bessatech.notify.listener;

import br.com.bessatech.notify.core.redis.entity.Notification;
import br.com.bessatech.notify.core.redis.repository.CustomSaver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class NotifyListener {
    private final CustomSaver customSaver;

    @Transactional
    @AfterReturning(value = "execution(* br.com.bessatech.notify.core.redis.repository.NotifyRepository.save(..))", returning = "result")
    public void saveRedisNotificationInMongo(Object result) {
        log.info("[NotifyListener::syncElasticPurchase] Double save in mongo database of redis key: {}", ((Notification) result).getId());
        Notification redisNotification = (Notification) result;
        customSaver.saveRedisNotificationInMongo(redisNotification);
    }

    @Transactional
    @AfterReturning(value = "execution(* br.com.bessatech.notify.core.redis.repository.CustomSaver.saveInBatch(..))", returning = "result")
    public void saveRedisNotificationInMongoBatch(Object result) {
        log.info("[NotifyListener::syncElasticPurchase] Double save in mongo database batch");
        List<Notification> notifications = (List<Notification>) result;
        notifications.forEach(customSaver::saveRedisNotificationInMongo);
    }
}
