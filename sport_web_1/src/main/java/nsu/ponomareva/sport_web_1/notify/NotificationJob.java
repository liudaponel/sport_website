package nsu.ponomareva.sport_web_1.notify;

import nsu.ponomareva.sport_web_1.models.UserEvent;
import nsu.ponomareva.sport_web_1.repository.UserEventRepository;
import nsu.ponomareva.sport_web_1.services.EmailService;
import nsu.ponomareva.sport_web_1.services.UserService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NotificationJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        UserEventRepository userEventRepository = (UserEventRepository) jobDataMap.get("userEventRepository");
        EmailService emailService = (EmailService) jobDataMap.get("emailService");
        Long event_id = jobDataMap.getLong("event_id");
        int N = jobDataMap.getInt("N");
        String event_name = jobDataMap.getString("event_name");

        List<UserEvent> users = userEventRepository.findByEventId(event_id).orElseThrow();
        String subject = "Спортивное мероприятие";
        String text = "Здравствуйте, уведомляем вас, что мероприятие " + event_name + " начнётся через " + N + "часов";
        for(UserEvent user : users){
            emailService.sendSimpleMessage(user.getUser_id().getEmail(), subject, text);
        }
    }
}

