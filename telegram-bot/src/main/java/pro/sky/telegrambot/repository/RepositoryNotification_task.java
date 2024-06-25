package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.Notification_task;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositoryNotification_task extends JpaRepository<Notification_task, Long> {

    List<Notification_task> findByChatIdAndMessageAndNotificationDateTime(Long chat_id,
                                                                          String message,
                                                                          LocalDateTime notificationDateTime
    );

    @Query(value = "select * from notification_task where notification_date=date_trunc('minute',LOCALTIMESTAMP)"
            , nativeQuery = true)
    List<Notification_task> getNotificationsForRun();
}
