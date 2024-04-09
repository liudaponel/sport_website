package nsu.ponomareva.sport_web_1.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nsu.ponomareva.sport_web_1.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import nsu.ponomareva.sport_web_1.exceptions.CustomException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    String jwtToken = service.register(request);
    if(jwtToken == null){
      throw new CustomException("Такой пользователь уже существует");
    }
    return ResponseEntity.ok(AuthenticationResponse.builder()
                              .accessToken(jwtToken)
                              .build());
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    String jwtToken = service.authenticate(request);
    if(!userService.isPasswordCorrect(request.getEmail(), request.getPassword())){
      throw new CustomException("Введен неверный адрес или пароль");
    }
    return ResponseEntity.ok(AuthenticationResponse.builder()
                              .accessToken(jwtToken)
                              .build());
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
