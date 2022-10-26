package com.thermostate.security.infrastucture;

import com.thermostate.security.application.TokenService;
import com.thermostate.security.model.LogedInUser;
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
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        String compactTokenString = Jwts.builder()
                .claim("jti", UUID.randomUUID())
                .claim("id", user.id())
                .claim("sub", user.name())
                .claim("email", user.email())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + compactTokenString;
    }

    @Override
    public LogedInUser parseToken(String token) {
        byte[] secretBytes = JWT_SECRET.getBytes();

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretBytes)
                .build()
                .parseClaimsJws(token);
        String username = jwsClaims.getBody().getSubject();
        Integer userId = jwsClaims.getBody().get("id", Integer.class);
        String email = jwsClaims.getBody().get("email", String.class);
        return new LogedInUser(username, email, userId);
    }
}
