package com.thermostate.security.infrastucture;

import com.thermostate.security.model.TokenService;
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

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        String compactTokenString = Jwts.builder()
                .claim("jti", UUID.randomUUID().toString())
                .claim("id", user.getId())
                .claim("sub", user.getName())
                .claim("email", user.getEmail())
                .setExpiration(null)
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
                .parseClaimsJws(token);
        String username = jwsClaims.getBody().getSubject();
        Integer userId = jwsClaims.getBody().get("id", Integer.class);
        String email = jwsClaims.getBody().get("email", String.class);
        return new LogedInUser(username, email, userId);
    }
}
