package br.com.bessatech.notify.service;

import br.com.bessatech.notify.core.dto.SimpleNotifyDTO;
import br.com.bessatech.notify.core.entity.Notify;
import br.com.bessatech.notify.core.repository.NotifyRepository;
import br.com.bessatech.notify.mapper.NotifyMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotifyService {

    private final NotifyRepository repository;
    private final NotifyMapper mapper;
    private final static String key = "notify-trigger";

    public void createNotify(SimpleNotifyDTO notifyDto) {
        Notify notify = mapper.toEntity(notifyDto);
        notify.setId(UUID.randomUUID().toString());
        notify.setExpires(1L);
        notify.setExpires(LocalDateTime.now().until(notifyDto.getExpirationDate(), ChronoUnit.MILLIS));
        repository.save(notify);
    }
}
