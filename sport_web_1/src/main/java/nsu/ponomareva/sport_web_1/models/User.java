package nsu.ponomareva.sport_web_1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
}