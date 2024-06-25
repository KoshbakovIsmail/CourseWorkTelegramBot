package pro.sky.telegrambot.scheduled;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import pro.sky.telegrambot.model.Notification_task;
import pro.sky.telegrambot.repository.RepositoryNotification_task;

import java.util.List;

@RequiredArgsConstructor
public class Scheduled {
    private final RepositoryNotification_task repositoryNotification_task;
    private final TelegramBot telegramBot;
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0/1 * * * *")
    public void runScheduled() {

        List<Notification_task> notification_task_List = repositoryNotification_task.getNotificationsForRun();
        for (Notification_task curTask : notification_task_List) {
            telegramBot.execute(new SendMessage(curTask.getChatId(), curTask.getMessage()));
        }
    }

}
