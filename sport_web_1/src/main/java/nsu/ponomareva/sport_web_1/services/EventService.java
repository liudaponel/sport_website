package nsu.ponomareva.sport_web_1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event newEventData) {
        Event eventToUpdate = eventRepository.findById(id).orElse(null);
        if (eventToUpdate != null) {
            // Установка новых данных
            eventToUpdate.setStart_time(newEventData.getStart_time());
            eventToUpdate.setTaken_places(newEventData.getTaken_places());
            eventToUpdate.setMax_places(newEventData.getMax_places());
            eventToUpdate.setDuration_hours(newEventData.getDuration_hours());
            eventToUpdate.setDuration_minutes(newEventData.getDuration_minutes());
            eventToUpdate.setPrice(newEventData.getPrice());
            eventToUpdate.setPlace(newEventData.getPlace());
            eventToUpdate.setCoach(newEventData.getCoach());
            // Сохранение обновленного объекта
            return eventRepository.save(eventToUpdate);
        } else {
            return null;
        }
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
