package nsu.ponomareva.sport_web_1.specifications;

import nsu.ponomareva.sport_web_1.models.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> hasFioLike(String fio) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.<String>get("fio"), "%" + fio + "%");
    }

    public static Specification<User> hasEmailLike(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.<String>get("email"), "%" + email + "%");
    }

    public static Specification<User> hasPhoneLike(String phone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.<String>get("phone_number"), "%" + phone + "%");
    }

    public static Specification<User> hasRole(Long role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role").get("role_id"), role);
    }

}
