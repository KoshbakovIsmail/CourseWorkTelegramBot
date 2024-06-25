package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notification_task;
import pro.sky.telegrambot.repository.RepositoryNotification_task;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private final RepositoryNotification_task repositoryNotification_task;

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            Long chat_id = update.message().chat().id();
            String messageText;
            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
            Matcher matcher;
            logger.info("Processing update: {}", update);
            if (update.message().text().equals("/start")) {
                messageText = "Привет";
                telegramBot.execute(new SendMessage(chat_id, messageText));
            } else {
                matcher = pattern.matcher(update.message().text());
                if (matcher.matches()) {
                    String date = matcher.group(1);
                    String notificationText = matcher.group(3);
                    LocalDateTime parseDateTime = LocalDateTime.parse(date,
                            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                    );
                    Notification_task notification_task = new Notification_task(
                            chat_id, notificationText, parseDateTime
                    );

                    messageText = addTask(notification_task);
                    telegramBot.execute(new SendMessage(chat_id, messageText));

                } else {
                    messageText = "Ошибка в формате задачи 'dd.MM.yyyy HH::mm Текст Задачи ";
                    telegramBot.execute(new SendMessage(chat_id, messageText));
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public String addTask(Notification_task notification_task) {
        List<Notification_task> notification_List = repositoryNotification_task
                .findByChatIdAndMessageAndNotificationDateTime(
                        notification_task.getChatId()
                        , notification_task.getMessage()
                        , notification_task.getNotificationDateTime()
                );
        if (notification_List.isEmpty()) {
            repositoryNotification_task.save(notification_task);
            return "Напоменание успешно добавлено!";
        } else {
            return "Такое напоминание уже существует!";
        }
    }


}
