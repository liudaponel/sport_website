package nsu.ponomareva.sport_web_1.services;

import nsu.ponomareva.sport_web_1.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import nsu.ponomareva.sport_web_1.models.Place;
import nsu.ponomareva.sport_web_1.repository.PlaceRepository;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public Place getPlaceById(Long id) {
        return placeRepository.findById(id).orElseThrow(()->new CustomException("Такого места не найдено"));
    }

    public Place addPlace(Place place) {
        return placeRepository.save(place);
    }

    public Place updatePlace(Long id, Place newPlaceData) {
        Place placeToUpdate = placeRepository.findById(id).orElse(null);
        if (placeToUpdate != null) {
            placeToUpdate.setName(newPlaceData.getName());
            placeToUpdate.setAddress(newPlaceData.getAddress());
            placeToUpdate.setMax_places(newPlaceData.getMax_places());
            return placeRepository.save(placeToUpdate);
        } else {
            return null;
        }
    }

    public void deletePlace(Long id) {
        placeRepository.deleteById(id);
    }
}
