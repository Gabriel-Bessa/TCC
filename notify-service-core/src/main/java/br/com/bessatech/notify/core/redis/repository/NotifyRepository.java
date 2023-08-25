package br.com.bessatech.notify.core.redis.repository;

import br.com.bessatech.notify.core.redis.entity.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotifyRepository extends CrudRepository<Notification, String> {
}
