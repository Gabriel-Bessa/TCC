package br.com.bessatech.notify.mapper;

import br.com.bessatech.notify.core.dto.SimpleNotifyDTO;
import br.com.bessatech.notify.core.entity.Notify;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyMapper {

    Notify toEntity(SimpleNotifyDTO dto);

}
