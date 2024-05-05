package nsu.ponomareva.sport_web_1.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserRequest {
    String fio;
    String email;
    String phone_number;
    String password;
    Long role;
}