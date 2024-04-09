package nsu.ponomareva.sport_web_1.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nsu.ponomareva.sport_web_1.exceptions.CustomException;
import nsu.ponomareva.sport_web_1.repository.RoleRepository;
import nsu.ponomareva.sport_web_1.repository.UserRepository;
import nsu.ponomareva.sport_web_1.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nsu.ponomareva.sport_web_1.models.User;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RoleRepository roleRepository;
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

  public String register(RegisterRequest request) {
    var user = User.builder()
        .fio(request.getFio())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .phone_number(request.getPhone_number())
        .role(roleRepository.findByName("Пользователь"))
        .build();
    var userFromDB = this.repository.findByEmail(user.getEmail());
    if(userFromDB.isPresent()){
      logger.info("найден пользователь с такими данными");
      return null;
    }
    repository.save(user);
    return jwtService.generateToken(user);
  }

  public String authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail());
    if(user.isEmpty()){
      return null;
    }
    return jwtService.generateToken(user.get());
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenExpired(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
