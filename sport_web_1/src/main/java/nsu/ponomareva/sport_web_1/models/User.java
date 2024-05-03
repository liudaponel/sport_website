package nsu.ponomareva.sport_web_1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(nullable=false, columnDefinition = "text")
    private String fio;
    @Column(nullable = false, columnDefinition = "text", unique=true)
    @Email
    private String email;
    @Column(nullable = false, columnDefinition = "text")
    @Pattern(regexp = "^(\\+)?\\d{11,12}$",
            message = "Указан некорректный номер телефона")
    private String phone_number;
    @Column(nullable = false, columnDefinition = "text")
    @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "users_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events = new HashSet<>();
}