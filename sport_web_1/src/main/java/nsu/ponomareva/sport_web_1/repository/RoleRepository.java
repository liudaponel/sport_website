package nsu.ponomareva.sport_web_1.repository;

import nsu.ponomareva.sport_web_1.models.Role;
import nsu.ponomareva.sport_web_1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT * FROM roles WHERE name =?1", nativeQuery = true)
    Role findByName(String name);
}
