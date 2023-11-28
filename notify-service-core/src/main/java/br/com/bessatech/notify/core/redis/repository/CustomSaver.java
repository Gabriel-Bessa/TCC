package br.com.bessatech.notify.core.redis.repository;

import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.mapper.NotifyMapper;
import br.com.bessatech.notify.core.mongo.document.MTriggerNotification;
import br.com.bessatech.notify.core.mongo.repository.MTriggerNotifyRepository;
import br.com.bessatech.notify.core.redis.dto.SimpleNotifyDTO;
import br.com.bessatech.notify.core.redis.entity.Notification;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomSaver {

    private static final String targetKeyspace = "notification-trigger";
    private final RedisTemplate<String, String> redisTemplate;
    private final MTriggerNotifyRepository mTriggerNotifyRepository;
    private final NotifyMapper mapper;

    public void saveRedisNotificationInMongo(Notification redisNotification) {
        MTriggerNotification mongoNotification = new MTriggerNotification();
        mongoNotification.setText(redisNotification.getText());
        mongoNotification.setTypes(redisNotification.getTypes());
        mongoNotification.setRedisId(redisNotification.getId());
        mongoNotification.setExpirationDate(redisNotification.getExpirationDate());
        mongoNotification.setStatus(NotificationStatus.WAITING_TRIGGER);
        mTriggerNotifyRepository.save(mongoNotification);
    }

    public List<Notification> saveInBatch(SimpleNotifyDTO notifyDto) {
        LocalDateTime now = LocalDateTime.now();
        List<Notification> notificationList = new ArrayList<>();
        for (int i = 0; i < notifyDto.getQuantity(); i++) {
            Notification notify = this.mapper.toEntity(notifyDto);
            notify.setId(UUID.randomUUID().toString());
            notify.setExpires(now.until(notifyDto.getExpirationDate(), ChronoUnit.MILLIS));
            redisTemplate.opsForValue().set(String.join(":", targetKeyspace, notify.getId()), "", notify.getExpires(), TimeUnit.MILLISECONDS);
            notificationList.add(notify);
        }
        return notificationList;
    }
}
