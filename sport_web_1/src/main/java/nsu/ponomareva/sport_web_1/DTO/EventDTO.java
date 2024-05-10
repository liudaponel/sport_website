package nsu.ponomareva.sport_web_1.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String name;
    private Timestamp start_time;
    private Integer taken_places;
    private Integer max_places;
    private Integer duration_hours;
    private Integer duration_minutes;
    private Integer price;
    private Long place;
    private Long coach;
    private String isSorted;
}
