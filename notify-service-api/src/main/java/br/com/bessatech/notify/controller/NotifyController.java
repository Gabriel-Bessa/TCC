package br.com.bessatech.notify.controller;

import br.com.bessatech.notify.config.ResponseDTO;
import br.com.bessatech.notify.core.redis.dto.SimpleNotifyDTO;
import br.com.bessatech.notify.service.NotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notification")
public class NotifyController {

    private final NotifyService service;

    @PostMapping("/trigger/sync")
    public ResponseDTO triggerSyncMessage(@RequestBody SimpleNotifyDTO notify) {
        log.info("[NotifyController::triggerSyncMessage] REST request to create notify for: {}", notify.getExpirationDate());
        return service.createNotifyTrigger(notify);
    }

    @PostMapping("/schedule/sync")
    public ResponseDTO scheduleMessage(@RequestBody SimpleNotifyDTO notify) {
        log.info("REST request to create notify");
        return service.createNotifyScheduler(notify);
    }
}
