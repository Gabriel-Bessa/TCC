package br.com.bessatech.notify.core.redis.dto;

import br.com.bessatech.notify.core.commons.enuns.NotificationType;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleNotifyDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String text;
    private String type;
    private LocalDateTime expirationDate;
    private Set<NotificationType> types;
    private Integer quantity;
}
