package br.com.bessatech.notify.core.mongo.repository;

import br.com.bessatech.notify.core.mongo.document.MTriggerNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MTriggerNotifyRepository extends MongoRepository<MTriggerNotification, String> {
}