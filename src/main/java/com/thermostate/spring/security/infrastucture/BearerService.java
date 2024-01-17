package com.thermostate.spring.security.infrastucture;

import com.thermostate.spring.security.model.TokenService;
import com.thermostate.spring.security.model.LogedInUser;
import com.thermostate.users.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class BearerService implements TokenService {
    private final String JWT_SECRET = """
    random string dfsdfenrñjweugb
    must be a long string in order to 
    have more than 256 bytes, so I think that I should 
    write some more characters here: 
    lets see: 1, 2, 3
""";

    @Override
    public String generateToken(User user) {
        if (user == null) {
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        String compactTokenString = Jwts.builder()
                .claim("jti", UUID.randomUUID().toString())
                .claim("id", user.getId())
                .claim("sub", user.getName())
                .claim("email", user.getEmail())
                .expiration(null)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + compactTokenString;
    }

    @Override
    public LogedInUser parseToken(String bearer) {
        String token = bearer.replace("Bearer ", "");
        byte[] secretBytes = JWT_SECRET.getBytes();

        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(secretBytes)
                .build()
                .parseSignedClaims(token);
        String username = jwsClaims.getPayload().getSubject();
        UUID userId = UUID.fromString(jwsClaims.getPayload().get("id", String.class));
        String email = jwsClaims.getPayload().get("email", String.class);
        return new LogedInUser(username, email, userId);
    }
}
