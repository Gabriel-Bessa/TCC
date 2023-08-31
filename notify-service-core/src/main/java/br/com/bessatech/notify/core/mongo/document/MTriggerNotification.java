package br.com.bessatech.notify.core.mongo.document;

import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.commons.enuns.NotificationType;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("notification-trigger")
public class MTriggerNotification implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String text;
    private LocalDateTime expirationDate;
    private Set<NotificationType> types;
    private NotificationStatus status;
}
