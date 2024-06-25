package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Notification_task;

public interface RepositoryNotification_task extends JpaRepository<Notification_task, Long> {
}
