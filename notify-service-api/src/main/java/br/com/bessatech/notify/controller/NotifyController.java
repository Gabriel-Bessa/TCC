package br.com.bessatech.notify.controller;

import br.com.bessatech.notify.core.dto.SimpleNotifyDTO;
import br.com.bessatech.notify.service.NotifyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notify")
public class NotifyController {

    private final NotifyService service;

    @PostMapping("/sync")
    public void syncMessage(@RequestBody SimpleNotifyDTO notify) throws JsonProcessingException {
        log.info("REST request to create notify");
        service.createNotify(notify);
    }

}
