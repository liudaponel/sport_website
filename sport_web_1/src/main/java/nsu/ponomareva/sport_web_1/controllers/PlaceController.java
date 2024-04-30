package nsu.ponomareva.sport_web_1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import nsu.ponomareva.sport_web_1.models.Place;
import nsu.ponomareva.sport_web_1.services.PlaceService;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "${url_frontend}")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<Place>> getAllPlaces() {
        List<Place> places = placeService.getAllPlaces();
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable Long id) {
        Place place = placeService.getPlaceById(id);
        if (place != null) {
            return new ResponseEntity<>(place, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Place> addPlace(@RequestBody Place place) {
        Place newPlace = placeService.addPlace(place);
        return new ResponseEntity<>(newPlace, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable Long id, @RequestBody Place place) {
        Place updatedPlace = placeService.updatePlace(id, place);
        if (updatedPlace != null) {
            return new ResponseEntity<>(updatedPlace, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

