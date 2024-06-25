package pro.sky.telegrambot.model;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity

public class Notification_task {
    private Long id;
    private Long chat_id;
    private String message;
    private LocalDateTime notificationDateTime;

    public Notification_task() {
    }

    public Notification_task(Long chat_id, String message, LocalDateTime notificationDateTime) {
        this.chat_id = chat_id;
        this.message = message;
        this.notificationDateTime = notificationDateTime;
    }
}
