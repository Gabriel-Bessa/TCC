package br.com.bessatech.notify.core.mapper;

import br.com.bessatech.notify.core.mongo.document.MSchedulerNotification;
import br.com.bessatech.notify.core.redis.dto.SimpleNotifyDTO;
import br.com.bessatech.notify.core.redis.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyMapper {

    Notification toEntity(SimpleNotifyDTO dto);
    MSchedulerNotification toMSchedulerNotification(SimpleNotifyDTO dto);

}
