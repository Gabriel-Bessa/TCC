package br.com.bessatech.notify.controller;

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
    public void triggerSyncMessage(@RequestBody SimpleNotifyDTO notify) {
        log.info("REST request to create notify");
        service.createNotifyTrigger(notify);
    }

    @PostMapping("/schedule/sync")
    public void scheduleMessage(@RequestBody SimpleNotifyDTO notify) {
        log.info("REST request to create notify");
        service.createNotifyTrigger(notify);
    }
}
