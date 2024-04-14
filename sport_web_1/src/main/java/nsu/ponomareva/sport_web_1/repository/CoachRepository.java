package nsu.ponomareva.sport_web_1.repository;

import nsu.ponomareva.sport_web_1.models.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}
