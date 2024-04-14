package nsu.ponomareva.sport_web_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, columnDefinition = "timestamp")
    private Timestamp start_time;
    private Integer taken_places;
    private Integer max_places;
    @Column(nullable=false)
    private Integer duration_hours;
    @Column(nullable=false)
    private Integer duration_minutes;
    private Integer price = 0;
    @ManyToOne
    @JoinColumn(name = "id")
    private Place place;
    @ManyToOne
    @JoinColumn(name = "id")
    private Coach coach;
}
