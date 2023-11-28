package br.com.bessatech.notify.service;

import br.com.bessatech.notify.config.ResponseDTO;
import br.com.bessatech.notify.config.exceptions.BusinessException;
import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.mongo.document.MSchedulerNotification;
import br.com.bessatech.notify.core.mongo.repository.MSchedulerNotifyRepository;
import br.com.bessatech.notify.core.mongo.repository.MTriggerNotifyRepository;
import br.com.bessatech.notify.core.redis.dto.SimpleNotifyDTO;
import br.com.bessatech.notify.core.redis.entity.Notification;
import br.com.bessatech.notify.core.redis.repository.CustomSaver;
import br.com.bessatech.notify.core.redis.repository.NotifyRepository;
import br.com.bessatech.notify.core.mapper.NotifyMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotifyService {

    private static final String KEY_SPACE = "notification-trigger";
    private final NotifyRepository repository;
    private final NotifyMapper mapper;
    private final MessageSource ms;
    private final CustomSaver customSaver;
    private final MSchedulerNotifyRepository mSchedulerNotifyRepository;

    private static void validateIfNotifyDateIsValid(SimpleNotifyDTO notifyDto) {
        if (LocalDateTime.now().plusSeconds(5).isAfter(notifyDto.getExpirationDate())) {
            throw new BusinessException("notify_exception", "notify.datetime.invalid");
        }
    }

    @Transactional
    public ResponseDTO createNotifyTrigger(SimpleNotifyDTO notifyDto) {
        validateIfNotifyDateIsValid(notifyDto);
        if (notifyDto.getQuantity() != null && notifyDto.getQuantity() > 1) {
            customSaver.saveInBatch(notifyDto);
            return new ResponseDTO("notification.success", "notification.create.success", ms);
        }
        Notification notify = mapper.toEntity(notifyDto);
        notify.setId(UUID.randomUUID().toString());
        notify.setExpires(LocalDateTime.now().until(notifyDto.getExpirationDate(), ChronoUnit.MILLIS));
        repository.save(notify);
        return new ResponseDTO("notification.success", "notification.create.success", ms);
    }

    public ResponseDTO createNotifyScheduler(SimpleNotifyDTO notify) {
        validateIfNotifyDateIsValid(notify);
        if (notify.getQuantity() != null && notify.getQuantity() > 1) {
            saveSchedulerMsgInBatch(notify);
            return new ResponseDTO("notification.success", "notification.create.success", ms);
        }
        MSchedulerNotification mSchedulerNotification = mapper.toMSchedulerNotification(notify);
        mSchedulerNotification.setStatus(NotificationStatus.WAITING_SCHEDULE);
        mSchedulerNotifyRepository.save(mSchedulerNotification);
        return new ResponseDTO("notification.success", "notification.create.success", ms);
    }

    private void saveSchedulerMsgInBatch(SimpleNotifyDTO notify) {
        List<MSchedulerNotification> schedulerNotifications = new ArrayList<>();
        for (int i = 0; i < notify.getQuantity(); i++) {
            MSchedulerNotification mSchedulerNotification = mapper.toMSchedulerNotification(notify);
            mSchedulerNotification.setStatus(NotificationStatus.WAITING_SCHEDULE);
            schedulerNotifications.add(mSchedulerNotification);
        }
        mSchedulerNotifyRepository.saveAll(schedulerNotifications);
    }
}
