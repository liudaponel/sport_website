package nsu.ponomareva.sport_web_1.services;

import nsu.ponomareva.sport_web_1.exceptions.CustomException;
import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.models.User;
import nsu.ponomareva.sport_web_1.repository.EventRepository;
import nsu.ponomareva.sport_web_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import nsu.ponomareva.sport_web_1.models.Registration;
import nsu.ponomareva.sport_web_1.repository.RegistrationRepository;
import nsu.ponomareva.sport_web_1.models.Registration_pk;

@Service
public class RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Registration addRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    public void deleteRegistration(Long userId, Long eventId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (userOptional.isPresent() && eventOptional.isPresent()) {
            registrationRepository.deleteById(new Registration_pk(userOptional.get(), eventOptional.get()));
        } else {
            throw new CustomException("Такой пользователь или мероприятие не найдено");
        }
    }
}
