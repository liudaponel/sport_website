package nsu.ponomareva.sport_web_1.repository;

import jakarta.transaction.Transactional;
import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query(value = "SELECT * FROM users WHERE email =?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE reset_token =?1", nativeQuery = true)
    Optional<User> findByResetToken(String token);
}