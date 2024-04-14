package nsu.ponomareva.sport_web_1.controllers;

import nsu.ponomareva.sport_web_1.models.Coach;
import nsu.ponomareva.sport_web_1.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches() {
        List<Coach> coaches = coachService.getAllCoaches();
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
        Coach newCoach = coachService.addCoach(coach);
        return new ResponseEntity<>(newCoach, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coach> updateCoach(@PathVariable Long id, @RequestBody Coach coach) {
        Coach updatedCoach = coachService.updateCoach(id, coach);
        if (updatedCoach != null) {
            return new ResponseEntity<>(updatedCoach, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        coachService.deleteCoach(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

