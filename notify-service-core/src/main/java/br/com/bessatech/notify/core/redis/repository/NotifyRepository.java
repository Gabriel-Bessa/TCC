package br.com.bessatech.notify.core.redis.repository;

import br.com.bessatech.notify.core.redis.entity.Notify;
import org.springframework.data.repository.CrudRepository;

public interface NotifyRepository extends CrudRepository<Notify, String> {
}
