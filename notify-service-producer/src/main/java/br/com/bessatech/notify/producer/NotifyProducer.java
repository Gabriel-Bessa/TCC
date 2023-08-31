package br.com.bessatech.notify.producer;

import br.com.bessatech.notify.core.commons.interfaces.BasicNotification;
import br.com.bessatech.notify.core.commons.topics.NotificationTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyProducer {

    private final StreamBridge streamBridge;

    public void sendToNotificationQueue(BasicNotification notification) {
        log.info("[NotifyProducer::sendToNotificationQueue] Producing");
        streamBridge.send(NotificationTopics.NOTIFICATION_TOPIC, notification);
    }

}
