package nsu.ponomareva.sport_web_1.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Optional;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;



/**
 * Contains all the methods needed to encode, decode and verify the JWT
 */
public class JwtUtils {
    private static @Value("${jwt.secret.access}") String SECRET_KEY;
    private static final String COOKIE_NAME = "token";
    private static final Algorithm JWT_ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
    private static final JWTVerifier JWT_VERIFIER = JWT.require(JWT_ALGORITHM).build();
    private static final int MAX_AGE_SECONDS = 1200;
    private static final int MAX_REFRESH_WINDOW_SECONDS = 30;


    static Cookie generateCookie(long id, int role) {
        Instant now = Instant.now();
        String token = JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(MAX_AGE_SECONDS))
                .withClaim("id", id)
                .withClaim("role", role)
                .sign(JWT_ALGORITHM);

        Cookie jwtCookie = new Cookie(COOKIE_NAME, token);
        jwtCookie.setMaxAge(MAX_AGE_SECONDS);
        return jwtCookie;
    }

    static Optional<String> getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (!cookie.getName().equals(COOKIE_NAME)) {
                continue;
            }
            return Optional.of(cookie.getValue());
        }
        return Optional.empty();
    }

    static Optional<DecodedJWT> getValidatedToken(String token) {
        try {
            // If the token is successfully verified, return its value
            return Optional.of(JWT_VERIFIER.verify(token));
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }

    // Gets the expiry timestamp from the request and returns true if it falls
    // within the allowed window, which starts at a given time before expiry
    static boolean isRefreshable(HttpServletRequest request) {
        Optional<String> token = getToken(request);
        if (token.isEmpty()) {
            return false;
        }
        Instant expiryTime = JWT.decode(token.get()).getExpiresAtAsInstant();
        Instant canBeRefreshedAfter = expiryTime.minusSeconds(MAX_REFRESH_WINDOW_SECONDS);
        return Instant.now().isAfter(canBeRefreshedAfter);
    }

}
