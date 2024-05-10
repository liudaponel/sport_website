package nsu.ponomareva.sport_web_1.services;

import jakarta.transaction.Transactional;
import nsu.ponomareva.sport_web_1.DTO.EmailDTO;
import nsu.ponomareva.sport_web_1.DTO.UserEventRequest;
import nsu.ponomareva.sport_web_1.DTO.UserEventRequestByEmail;
import nsu.ponomareva.sport_web_1.exceptions.CustomException;
import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.models.User;
import nsu.ponomareva.sport_web_1.models.UserEvent;
import nsu.ponomareva.sport_web_1.repository.EventRepository;
import nsu.ponomareva.sport_web_1.repository.UserEventRepository;
import nsu.ponomareva.sport_web_1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserEventService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserEventRepository userEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EmailService emailService;

    public List<UserEvent> getAllUserEvents() {
        return userEventRepository.findAll();
    }

    public List<UserEvent> getUserEvents(Long user_id){
        List<UserEvent> events_id = userEventRepository.findByUserId(user_id).orElseThrow(() -> new CustomException("Этот пользователь никуда не зарегистрирован"));
        List<UserEvent> events = new ArrayList<>();
        for (UserEvent e: events_id) {
            UserEvent ue = new UserEvent();
            ue.setEvent_id(e.getEvent_id());
            ue.setUsers_events_id(e.getUsers_events_id());
            events.add(ue);
        }

        return events;
    }

    public List<UserEvent> getNotChecked(){
        return userEventRepository.findNotChecked().orElseThrow();
    }

    public UserEvent getUserEventById(Long id) {
        return userEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserEvent not found with id " + id));
    }

    @Transactional
    public UserEvent createUserEvent(UserEventRequest userEvent) {
        UserEvent ue = new UserEvent();
        User user = userRepository.findById(userEvent.getUser()).orElseThrow();
        Event event = eventRepository.findById(userEvent.getEvent()).orElseThrow();
        if(Objects.equals(event.getTaken_places(), event.getMax_places())){
            throw new CustomException("Все места заняты");
        }
        event.setTaken_places(event.getTaken_places() + 1);
        eventRepository.save(event);

        ue.setUser_id(user);
        ue.setEvent_id(event);

        String subject = "Запись на мероприятие";
        String text = "Здравствуйте! Ваша заявка на участие в спортивном мероприятии была отправлена на модерацию администратору.";
        emailService.sendSimpleMessage(user.getEmail(), subject, text);

        return userEventRepository.save(ue);
    }

    @Transactional
    public UserEvent createUserEventByEmail(UserEventRequestByEmail userEvent) {
        UserEvent ue = new UserEvent();
        User user = userRepository.findByEmail(userEvent.getUserEmail()).orElseThrow();
        Event event = eventRepository.findById(userEvent.getEvent()).orElseThrow();
        if(Objects.equals(event.getTaken_places(), event.getMax_places())){
            throw new CustomException("Все места заняты");
        }
        event.setTaken_places(event.getTaken_places() + 1);
        eventRepository.save(event);

        ue.setUser_id(user);
        ue.setEvent_id(event);

        String subject = "Запись на мероприятие";
        String text = "Здравствуйте! Ваша заявка на участие в спортивном мероприятии " + event.getName() + " была отправлена на модерацию администратору.";
        emailService.sendSimpleMessage(user.getEmail(), subject, text);

        return userEventRepository.save(ue);
    }

    public void makeChecked(Long id) {
        UserEvent existingUserEvent = userEventRepository.findById(id)
                .orElseThrow(() -> new CustomException("UserEvent not found with id " + id));
        existingUserEvent.setChecked(true);
        userEventRepository.save(existingUserEvent);

        String subject = "Запись на мероприятие";
        String text = "Здравствуйте! Вы зарегистрированы на мероприятие " + existingUserEvent.getEvent_id().getName();
        emailService.sendSimpleMessage(existingUserEvent.getUser_id().getEmail(), subject, text);
    }

    public void deleteUserEvent(Long id) {
        userEventRepository.deleteByIdMy(id);
    }
}
