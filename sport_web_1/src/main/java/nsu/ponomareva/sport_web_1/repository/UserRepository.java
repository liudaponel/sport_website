package nsu.ponomareva.sport_web_1.repository;

import nsu.ponomareva.sport_web_1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE email =?1", nativeQuery = true)
    Optional<User> findByEmail(String email);
}