package br.com.bessatech.notify.core.commons.enuns;

import java.util.List;

public enum NotificationStatus {
    SEND,
    PROCESSING,
    WAITING_TRIGGER,
    WAITING_SCHEDULE;

    public static List<NotificationStatus> waitingStatus() {
        return List.of(WAITING_SCHEDULE, WAITING_TRIGGER);
    }
}
