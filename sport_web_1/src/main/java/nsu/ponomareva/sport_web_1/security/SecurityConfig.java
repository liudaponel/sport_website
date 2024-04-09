//package nsu.ponomareva.sport_web_1.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//@Configuration
//public class SecurityConfig {
//
//    private final JwtFilter jwtFilter = new JwtFilter();
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(
//                        authz -> authz
//                                .antMatchers("/api/auth/**").permitAll()
//                                .anyRequest().authenticated()
//                                .and()
//                                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                ).build();
//    }
//}
