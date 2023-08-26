package br.com.bessatech.notify.config;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Getter
@Setter
public class ResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String property;
    private String message;

    public ResponseDTO(String property, String message, MessageSource ms) {
        this(property, message, ms, new Object[0]);
    }

    public ResponseDTO(String property, String message, MessageSource ms, Object[] value) {
        this.property = ms.getMessage(property, value, LocaleContextHolder.getLocale());
        this.message = ms.getMessage(message, value, LocaleContextHolder.getLocale());
    }
}
