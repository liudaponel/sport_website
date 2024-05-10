package nsu.ponomareva.sport_web_1.controllers;

import nsu.ponomareva.sport_web_1.DTO.UserEventRequest;
import nsu.ponomareva.sport_web_1.DTO.UserEventRequestByEmail;
import nsu.ponomareva.sport_web_1.models.UserEvent;
import nsu.ponomareva.sport_web_1.services.UserEventService;
import nsu.ponomareva.sport_web_1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "${url_frontend}")
public class UserEventController {
    @Autowired
    private UserEventService userEventService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Get all user events
    @GetMapping
    public ResponseEntity<List<UserEvent>> getAllUserEvents() {
        List<UserEvent> userEvents = userEventService.getAllUserEvents();
        return new ResponseEntity<>(userEvents, HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<UserEvent>> getEventsByUser(@PathVariable Long user_id){
        List<UserEvent> events = userEventService.getUserEvents(user_id);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/not_checked")
    public ResponseEntity<List<UserEvent>> getNotChecked(){
        logger.info("get not checked");
        List<UserEvent> events = userEventService.getNotChecked();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Create user event by user id
    @PostMapping("/byId")
    public ResponseEntity<UserEvent> createUserEvent(@RequestBody UserEventRequest userEvent) {
        UserEvent createdUserEvent = userEventService.createUserEvent(userEvent);
        return new ResponseEntity<>(createdUserEvent, HttpStatus.CREATED);
    }

    @PostMapping("/byEmail")
    public ResponseEntity<UserEvent> createUserEventByEmail(@RequestBody UserEventRequestByEmail userEvent) {
        UserEvent createdUserEvent = userEventService.createUserEventByEmail(userEvent);
        return new ResponseEntity<>(createdUserEvent, HttpStatus.CREATED);
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<UserEvent> makeChecked(@PathVariable Long id) {
        userEventService.makeChecked(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Delete user event
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserEvent(@PathVariable Long id) {
        userEventService.deleteUserEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
