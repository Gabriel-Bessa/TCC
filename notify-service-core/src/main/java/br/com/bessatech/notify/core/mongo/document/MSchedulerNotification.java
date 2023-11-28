package br.com.bessatech.notify.core.mongo.document;

import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.commons.enuns.NotificationType;
import br.com.bessatech.notify.core.commons.interfaces.BasicNotification;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
@Document("notification-scheduler")
public class MSchedulerNotification implements Serializable, BasicNotification {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    private String text;
    private LocalDateTime expirationDate;
    private Set<NotificationType> types;
    private NotificationStatus status;
}
