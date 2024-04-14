package nsu.ponomareva.sport_web_1.repository;

import nsu.ponomareva.sport_web_1.models.Registration;
import nsu.ponomareva.sport_web_1.models.Registration_pk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Registration_pk> {
}
