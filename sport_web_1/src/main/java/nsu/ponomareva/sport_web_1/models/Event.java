package nsu.ponomareva.sport_web_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;
    @Column(columnDefinition = "text")
    private String name;
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
    @JoinColumn(name = "place_id")
    private Place place;
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToMany(mappedBy = "events")
    private Set<User> users = new HashSet<>();
}
