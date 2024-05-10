package nsu.ponomareva.sport_web_1.specifications;

import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.repository.CoachRepository;
import nsu.ponomareva.sport_web_1.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

import java.sql.Date;
import java.sql.Timestamp;

public class EventSpecification {
    public static Specification<Event> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.<String>get("name"), "%" + name + "%");
    }

    public static Specification<Event> hasPlace(Long place_id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("place").get("place_id"), place_id);
    }

    public static Specification<Event> hasStart_time(Timestamp start_time) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("start_date"), start_time);
    }

    public static Specification<Event> hasDurationHours(Integer duration_hours) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("duration_hours"), duration_hours);
    }

    public static Specification<Event> hasDurationMinutes(Integer duration_minutes) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("duration_minutes"), duration_minutes);
    }

    public static Specification<Event> hasPrice(Integer price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("price"), price);
    }

    public static Specification<Event> hasCoach(Long coach_id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("coach").get("user_id"), coach_id);
    }
}
