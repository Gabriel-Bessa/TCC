package br.com.bessatech.notify.integration;

import br.com.bessatech.notify.core.commons.interfaces.BasicNotification;
import br.com.bessatech.notify.integration.feign.SocketFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocketService {

    private final SocketFeignClient feignClient;

    public void sync(BasicNotification notification) {
        feignClient.sync(notification);
    }

    public void syncBatch(List<? extends BasicNotification> notifications) {
        feignClient.syncBatch(notifications);
    }
}
