package nsu.ponomareva.sport_web_1.repository;

import nsu.ponomareva.sport_web_1.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
