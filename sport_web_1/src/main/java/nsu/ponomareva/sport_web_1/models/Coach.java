package nsu.ponomareva.sport_web_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "coaches")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coach {
    @Id
    private Long user_id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    private Integer salary;
}
