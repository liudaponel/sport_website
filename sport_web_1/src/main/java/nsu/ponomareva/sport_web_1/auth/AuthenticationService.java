package nsu.ponomareva.sport_web_1.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import nsu.ponomareva.sport_web_1.exceptions.CustomException;
import nsu.ponomareva.sport_web_1.repository.RoleRepository;
import nsu.ponomareva.sport_web_1.repository.UserRepository;
import nsu.ponomareva.sport_web_1.security.JwtService;
import nsu.ponomareva.sport_web_1.security.UserDetailsImpl;
import nsu.ponomareva.sport_web_1.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nsu.ponomareva.sport_web_1.models.User;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private final EmailService emailService;
    @Value("${url_frontend}")
    private String urlFrontend;

    public String register(User request) {
        var user = User.builder()
            .fio(request.getFio())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .phone_number(request.getPhone_number())
            .role(roleRepository.findByName("Пользователь"))
            .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        var userFromDB = this.repository.findByEmail(user.getEmail());
        if(userFromDB.isPresent()) {
            return null;
        }
        repository.save(user);
        return jwtService.generateToken(userDetails);
    }

    public String authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }
        catch (AuthenticationException ex){
            throw new CustomException("Введен неверный адрес или пароль");
        }
        var user = repository.findByEmail(request.getEmail());
        if(user.isEmpty()){
            return null;
        }
        return jwtService.generateToken(new UserDetailsImpl(user.get()));
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
            if (jwtService.isTokenExpired(refreshToken, new UserDetailsImpl(user))) {
                var accessToken = jwtService.generateToken(new UserDetailsImpl(user));
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
      }

    public void resetPassword(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException("Пользователь с таким email не найден"));
        String token = UUID.randomUUID().toString();
        user.setReset_token(token);
        userRepository.save(user);

        String subject = "Восстановление пароля";
        String url = urlFrontend + "/reset-password/" + token;
        String text = "Ссылка для восстановления пароля: " + url;
        emailService.sendSimpleMessage(userEmail, subject, text);
    }

    public void changePassword(String newPassword, String token){
        User user = userRepository.findByResetToken(token).orElseThrow(() -> new CustomException("По этой ссылке нельзя обновить пароль"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setReset_token(null);
        userRepository.save(user);
    }

}
