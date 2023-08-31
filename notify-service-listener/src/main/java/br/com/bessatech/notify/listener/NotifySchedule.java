package br.com.bessatech.notify.listener;

import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.mongo.document.MTriggerNotification;
import br.com.bessatech.notify.producer.NotifyProducer;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifySchedule {

    private final MongoTemplate mongoTemplate;
    private final NotifyProducer producer;

    @Scheduled(fixedRateString = "${notify.scheduled.interval:5000}")
    public void reportCurrentTime() {
        Query query = findNotNotifyData();
        List<MTriggerNotification> mTriggerNotifications = mongoTemplate.find(query, MTriggerNotification.class);
        if (!CollectionUtils.isEmpty(mTriggerNotifications)) {
            log.info("[NotifySchedule::reportCurrentTime] Runnig schuling...");
            mTriggerNotifications.forEach(producer::sendToNotificationQueue);
            markAsUpdated(query);
        }
    }

    private void markAsUpdated(Query query) {
        Update update = new Update();
        update.set("status", NotificationStatus.SEND);
        mongoTemplate.updateMulti(query, update, MTriggerNotification.class);
    }

    private Query findNotNotifyData() {
        Query query = new Query();
        Criteria criteria = Criteria.where("status").in(NotificationStatus.WAITING_SCHEDULE, NotificationStatus.WAITING_TRIGGER);
        criteria.and("expirationDate").lte(LocalDateTime.now());
        query.addCriteria(criteria);
        return query;
    }

}
