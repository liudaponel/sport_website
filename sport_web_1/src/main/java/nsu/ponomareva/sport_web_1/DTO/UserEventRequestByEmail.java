package nsu.ponomareva.sport_web_1.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEventRequestByEmail {
    String userEmail;
    Long event;
}
