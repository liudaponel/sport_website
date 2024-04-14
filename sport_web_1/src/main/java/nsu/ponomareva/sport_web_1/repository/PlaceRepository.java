package nsu.ponomareva.sport_web_1.repository;

import nsu.ponomareva.sport_web_1.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
