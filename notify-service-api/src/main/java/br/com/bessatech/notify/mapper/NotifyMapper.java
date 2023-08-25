package br.com.bessatech.notify.mapper;

import br.com.bessatech.notify.core.redis.dto.SimpleNotifyDTO;
import br.com.bessatech.notify.core.redis.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyMapper {

    Notification toEntity(SimpleNotifyDTO dto);

}
