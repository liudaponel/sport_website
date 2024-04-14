package nsu.ponomareva.sport_web_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "registrations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Registration_pk.class)
public class Registration {
    @ManyToOne
    @JoinColumn(name = "id")
    @Id
    private User user;
    @ManyToOne
    @JoinColumn(name = "id")
    @Id
    private Event event;
}
