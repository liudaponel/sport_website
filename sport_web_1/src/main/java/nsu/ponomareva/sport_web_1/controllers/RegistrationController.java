package nsu.ponomareva.sport_web_1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import nsu.ponomareva.sport_web_1.models.Registration;
import nsu.ponomareva.sport_web_1.services.RegistrationService;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        List<Registration> registrations = registrationService.getAllRegistrations();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Registration> addRegistration(@RequestBody Registration registration) {
        Registration newRegistration = registrationService.addRegistration(registration);
        return new ResponseEntity<>(newRegistration, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long userId, @PathVariable Long eventId) {
        registrationService.deleteRegistration(userId, eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
