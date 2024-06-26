package nsu.ponomareva.sport_web_1.controllers;

import nsu.ponomareva.sport_web_1.models.Coach;
import nsu.ponomareva.sport_web_1.services.CoachService;
import nsu.ponomareva.sport_web_1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@CrossOrigin(origins = "${url_frontend}")
public class CoachController {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CoachService coachService;

    @GetMapping
    public ResponseEntity<Page<Coach>> getAllCoaches(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Page<Coach> coaches = coachService.getAllCoaches(page, size);
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coach> getCoachById(@PathVariable Long id) {
        Coach coach = coachService.getCoachById(id);
        if (coach != null) {
            return new ResponseEntity<>(coach, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Coach> addCoach(@RequestBody Coach coach) {
        coachService.addCoach(coach);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coach> updateCoach(@PathVariable Long id, @RequestBody Coach coach) {
        coachService.updateCoach(coach, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        coachService.deleteCoach(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

