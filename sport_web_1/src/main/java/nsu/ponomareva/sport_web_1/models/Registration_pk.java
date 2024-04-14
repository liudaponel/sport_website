package nsu.ponomareva.sport_web_1.models;

import lombok.EqualsAndHashCode;
import java.io.Serializable;

@EqualsAndHashCode
public class Registration_pk implements Serializable {
    private User user;
    private Event event;
    public Registration_pk(User user, Event event) {
        this.user = user;
        this.event = event;
    }
}
