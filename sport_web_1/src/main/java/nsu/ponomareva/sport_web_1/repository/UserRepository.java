package nsu.ponomareva.sport_web_1.repository;

import nsu.ponomareva.sport_web_1.models.Role;
import nsu.ponomareva.sport_web_1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query(value = "SELECT * FROM users WHERE fio =?1 AND email =?2 AND phone_number =?3", nativeQuery = true)
//    User findUserByInfo(String fio, String email, String phone_number);
//
//    @Query(value = "SELECT * FROM users WHERE fio =?1 AND email =?2 AND phone_number =?3 AND password =?4", nativeQuery = true)
//    User findUserByInfo(String fio, String email, String phone_number, String password);

    @Query(value = "SELECT * FROM users WHERE email =?1", nativeQuery = true)
    Optional<User> findByEmail(String email);
}