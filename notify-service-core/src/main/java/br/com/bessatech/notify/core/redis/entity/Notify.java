package br.com.bessatech.notify.core.redis.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@RedisHash("notify-trigger")
public class Notify implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Indexed
    private String id;
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expires;
    private String text;
    private String type;
    private LocalDateTime expirationDate;
}
