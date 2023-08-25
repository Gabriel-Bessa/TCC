package br.com.bessatech.notify.service;

import br.com.bessatech.notify.config.exceptions.BusinessException;
import br.com.bessatech.notify.core.redis.dto.SimpleNotifyDTO;
import br.com.bessatech.notify.core.redis.entity.Notification;
import br.com.bessatech.notify.core.redis.repository.NotifyRepository;
import br.com.bessatech.notify.mapper.NotifyMapper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotifyService {

    private final NotifyRepository repository;
    private final NotifyMapper mapper;

    @Transactional
    public void createNotifyTrigger(SimpleNotifyDTO notifyDto) {
        if (LocalDateTime.now().plusSeconds(5).isAfter(notifyDto.getExpirationDate())) {
            throw new BusinessException("notify_exception", "notify.datetime.invalid");
        }
        Notification notify = mapper.toEntity(notifyDto);
        notify.setId(UUID.randomUUID().toString());
        notify.setExpires(LocalDateTime.now().until(notifyDto.getExpirationDate(), ChronoUnit.MILLIS));
        repository.save(notify);
    }
}
