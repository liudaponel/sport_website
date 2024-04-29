package nsu.ponomareva.sport_web_1.services;

import nsu.ponomareva.sport_web_1.exceptions.CustomException;
import nsu.ponomareva.sport_web_1.models.Coach;
import nsu.ponomareva.sport_web_1.models.User;
import nsu.ponomareva.sport_web_1.repository.CoachRepository;
import nsu.ponomareva.sport_web_1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    public Coach getCoachById(Long id) {
        return coachRepository.findById(id).orElse(null);
    }

    public void addCoach(Coach coach) {
        User user = userRepository.findById(coach.getUser_id()).orElseThrow(()->new CustomException("Такого пользователя не существует"));
        coach.setUser(user);
        coach.setUser_id(null);
        coachRepository.save(coach);
    }

    public void updateCoach(Coach coach, Long id){
        User user = userRepository.findById(id).orElseThrow(()->new CustomException("Такого пользователя не существует"));
        coach.setUser(user);
        coach.setUser_id(null);
        coachRepository.save(coach);
    }

    public void deleteCoach(Long id) {
        coachRepository.deleteById(id);
    }
}

