package nsu.ponomareva.sport_web_1.auth;

import nsu.ponomareva.sport_web_1.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String fio;
  private String email;
  private String phone_number;
  private String password;
  private Role role;
}
