package nsu.ponomareva.sport_web_1.controllers;

import nsu.ponomareva.sport_web_1.DTO.EventDTO;
import nsu.ponomareva.sport_web_1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.services.EventService;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "${url_frontend}")
public class EventController {

    @Autowired
    private EventService eventService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @GetMapping
    public ResponseEntity<Page<Event>> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size) {
        Page<Event> events = eventService.getAllEvents(page, size);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody EventDTO request) {
        eventService.addEvent(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody EventDTO request) {
        Event updatedEvent = eventService.updateEvent(id, request);
        if (updatedEvent != null) {
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/filters")
    ResponseEntity<Page<Event>> getWithFilters(@RequestBody EventDTO request,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size){
        Page<Event> events = eventService.getWithFilters(request, page, size);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
