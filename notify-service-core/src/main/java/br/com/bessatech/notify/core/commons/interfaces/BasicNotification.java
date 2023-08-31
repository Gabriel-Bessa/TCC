package br.com.bessatech.notify.core.commons.interfaces;

import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.commons.enuns.NotificationType;
import java.time.LocalDateTime;
import java.util.Set;

public interface BasicNotification {

    String getId();
    String getText();
    LocalDateTime getExpirationDate();
    Set<NotificationType> getTypes();
    NotificationStatus getStatus();

}
