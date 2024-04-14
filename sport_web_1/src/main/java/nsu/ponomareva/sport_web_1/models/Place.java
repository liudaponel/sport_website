package nsu.ponomareva.sport_web_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "places")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable=false, columnDefinition = "text")
    private String name;
    @Column(nullable = false, columnDefinition = "text")
    private String address;
    @Column(nullable = false)
    private Integer max_places;
}
