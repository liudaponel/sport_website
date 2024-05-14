package nsu.ponomareva.sport_web_1.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nsu.ponomareva.sport_web_1.DTO.ChangePasswordDTO;
import nsu.ponomareva.sport_web_1.repository.UserRepository;
import nsu.ponomareva.sport_web_1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Valid;


import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import nsu.ponomareva.sport_web_1.exceptions.CustomException;
import nsu.ponomareva.sport_web_1.models.User;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "${url_frontend}")
public class AuthenticationController {
      private final AuthenticationService service;
      private final UserService userService;
      private final PasswordEncoder passwordEncoder;
      private static final Logger logger = LoggerFactory.getLogger(UserService.class);

      @PostMapping("/register")
      public ResponseEntity<AuthenticationResponse> register(
              @RequestBody @Valid User user
      ) {
          String jwtToken = service.register(user);
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
        logger.info(jwtToken);
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

      @PostMapping("/reset-password")
      public ResponseEntity<String> resetPassword(HttpServletRequest request,
                                           @RequestParam("email") String userEmail) {
          service.resetPassword(userEmail);
          return ResponseEntity.ok("");
      }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto, @RequestParam("token") String token){
        service.changePassword(dto.getNewPassword(), token);
        return ResponseEntity.ok("Пароль успешно обновлён.");
    }
}
