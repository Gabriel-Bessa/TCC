package br.com.bessatech.notify.core.redis.entity;

import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.commons.enuns.NotificationType;
import br.com.bessatech.notify.core.commons.interfaces.BasicNotification;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RedisHash("notification-trigger")
public class Notification implements Serializable, BasicNotification {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expires;
    private String text;
    private String type;
    private LocalDateTime expirationDate;
    private Set<NotificationType> types;
    private NotificationStatus status;

}
