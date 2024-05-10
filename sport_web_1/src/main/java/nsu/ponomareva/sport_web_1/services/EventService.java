package nsu.ponomareva.sport_web_1.services;

import nsu.ponomareva.sport_web_1.DTO.EventDTO;
import nsu.ponomareva.sport_web_1.repository.CoachRepository;
import nsu.ponomareva.sport_web_1.repository.PlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.repository.EventRepository;
import nsu.ponomareva.sport_web_1.specifications.EventSpecification;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private PlaceRepository placeRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public void addEvent(EventDTO request) {
        Event event = new Event();
        event.setDuration_hours(request.getDuration_hours());
        event.setStart_time(request.getStart_time());
        event.setDuration_minutes(request.getDuration_minutes());
        event.setTaken_places(request.getTaken_places());
        event.setMax_places(request.getMax_places());
        event.setPlace(placeRepository.findById(request.getPlace()).orElseThrow());
        event.setCoach(coachRepository.findById(request.getCoach()).orElseThrow());
        event.setName(request.getName());
        event.setPrice(request.getPrice());

        eventRepository.save(event);
    }

    public Event updateEvent(Long id, EventDTO newEventData) {
        Event eventToUpdate = eventRepository.findById(id).orElse(null);
        if (eventToUpdate != null) {
            eventToUpdate.setStart_time(newEventData.getStart_time());
            eventToUpdate.setTaken_places(newEventData.getTaken_places());
            eventToUpdate.setMax_places(newEventData.getMax_places());
            eventToUpdate.setDuration_hours(newEventData.getDuration_hours());
            eventToUpdate.setDuration_minutes(newEventData.getDuration_minutes());
            eventToUpdate.setPrice(newEventData.getPrice());
            eventToUpdate.setPlace(placeRepository.findById(newEventData.getPlace()).orElseThrow());
            eventToUpdate.setCoach(coachRepository.findById(newEventData.getCoach()).orElseThrow());
            eventToUpdate.setName(newEventData.getName());

            return eventRepository.save(eventToUpdate);
        } else {
            return null;
        }
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getWithFilters(EventDTO request) {
        Specification<Event> spec = Specification.where(null);

        if (request.getName() != null) {
            spec = spec.and(EventSpecification.hasNameLike(request.getName()));
        }
        if (request.getPlace() != null) {
            spec = spec.and(EventSpecification.hasPlace(request.getPlace()));
        }
        if (request.getStart_time() != null) {
            spec = spec.and(EventSpecification.hasStart_time(request.getStart_time()));
        }
        if (request.getPrice() != null) {
            spec = spec.and(EventSpecification.hasPrice(request.getPrice()));
        }
        if (request.getCoach() != null) {
            spec = spec.and(EventSpecification.hasCoach(request.getCoach()));
        }
        if (request.getDuration_hours() != null) {
            spec = spec.and(EventSpecification.hasDurationHours(request.getDuration_hours()));
        }
        if (request.getDuration_minutes() != null) {
            spec = spec.and(EventSpecification.hasDurationMinutes(request.getDuration_minutes()));
        }


        return eventRepository.findAll(spec);
    }

}
