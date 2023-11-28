package br.com.bessatech.notify.integration.feign;

import br.com.bessatech.notify.core.commons.interfaces.BasicNotification;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "socket-feign", url = "${notify.socket.url}")
public interface SocketFeignClient {

    @PostMapping("/sync")
    void sync(@RequestBody BasicNotification notification);

    @PostMapping("/sync/batch")
    void syncBatch(List<? extends BasicNotification> notifications);
}
