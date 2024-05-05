package nsu.ponomareva.sport_web_1.repository;

import jakarta.transaction.Transactional;
import nsu.ponomareva.sport_web_1.models.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
    @Query(value = "SELECT * FROM users_events WHERE user_id =?1 AND is_checked =true", nativeQuery = true)
    Optional<List<UserEvent>> findByUserId(Long user_id);

    @Query(value = "SELECT * FROM users_events WHERE is_checked =false", nativeQuery = true)
    Optional<List<UserEvent>> findNotChecked();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users_events WHERE users_events_id =?1", nativeQuery = true)
    void deleteByIdMy(Long id);
}
