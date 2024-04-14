package nsu.ponomareva.sport_web_1.services;

import nsu.ponomareva.sport_web_1.models.Coach;
import nsu.ponomareva.sport_web_1.repository.CoachRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    public Coach getCoachById(Long id) {
        return coachRepository.findById(id).orElse(null);
    }

    public Coach addCoach(Coach coach) {
        return coachRepository.save(coach);
    }

    public Coach updateCoach(Long id, Coach newCoachData) {
        Coach coachToUpdate = coachRepository.findById(id).orElse(null);
        if (coachToUpdate != null) {
            // Установка новых данных
            coachToUpdate.setUser_id(newCoachData.getUser_id());
            // Сохранение обновленного объекта
            return coachRepository.save(coachToUpdate);
        } else {
            return null;
        }
    }

    public void deleteCoach(Long id) {
        coachRepository.deleteById(id);
    }
}

