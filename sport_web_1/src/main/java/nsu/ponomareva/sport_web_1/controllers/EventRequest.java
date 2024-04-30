package nsu.ponomareva.sport_web_1.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    private String name;
    private Timestamp start_time;
    private Integer taken_places;
    private Integer max_places;
    private Integer duration_hours;
    private Integer duration_minutes;
    private Integer price;
    private Long place;
    private Long coach;
}
