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
public class EventsReportDTO {
    String event;
    Long count_people;
    Timestamp date;
    Long event_id;
}
