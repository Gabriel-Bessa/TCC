package br.com.bessatech.notify.core.repository;

import br.com.bessatech.notify.core.entity.Notify;
import org.springframework.data.repository.CrudRepository;

public interface NotifyRepository extends CrudRepository<Notify, String> {
}
