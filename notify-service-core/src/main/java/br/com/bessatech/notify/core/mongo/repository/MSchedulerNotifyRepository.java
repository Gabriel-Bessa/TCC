package br.com.bessatech.notify.core.mongo.repository;

import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.mongo.document.MSchedulerNotification;
import br.com.bessatech.notify.core.mongo.document.MTriggerNotification;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MSchedulerNotifyRepository extends MongoRepository<MSchedulerNotification, String> {

    List<MSchedulerNotification> findAllByStatus(NotificationStatus status);

}