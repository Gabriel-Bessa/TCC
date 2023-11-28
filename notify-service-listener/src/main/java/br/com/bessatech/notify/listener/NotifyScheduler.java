package br.com.bessatech.notify.listener;

import br.com.bessatech.notify.core.commons.enuns.NotificationStatus;
import br.com.bessatech.notify.core.mongo.document.MSchedulerNotification;
import br.com.bessatech.notify.core.mongo.repository.MSchedulerNotifyRepository;
import br.com.bessatech.notify.integration.SocketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyScheduler {
    private final MSchedulerNotifyRepository mSchedulerNotifyRepository;
    private final SocketService socketService;
    @Scheduled(cron = "${notify.cron}")
    public void schedulerNotify() {
        log.info("[NotifyScheduler::schedulerNotify] Running scheduler of notifications");
        List<MSchedulerNotification> allNotSend = mSchedulerNotifyRepository.findAllByStatus(NotificationStatus.WAITING_SCHEDULE);
        if (!CollectionUtils.isEmpty(allNotSend)) {
            try {
                socketService.syncBatch(allNotSend);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

}
