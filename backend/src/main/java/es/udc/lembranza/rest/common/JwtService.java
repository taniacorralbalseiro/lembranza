package es.udc.lembranza.rest.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMinutes;

    public JwtService(@Value("${app.jwt.signKey}") String signKey,
                      @Value("${app.jwt.expirationMinutes:60}") long expirationMinutes) {

        this.key = Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(UserDetails user) {
        var now = Instant.now();
        var exp = now.plusSeconds(expirationMinutes * 60);

        var claims = Map.<String, Object>of(
                "roles", user.getAuthorities().stream().map(a -> a.getAuthority()).toList()
        );

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())          // email/username
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        var username = extractUsername(token);
        return username.equals(user.getUsername()) && !isExpired(token);
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isExpired(String token) {
        var exp = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return exp.before(new Date());
    }

    public long getExpirationSeconds() {
        return expirationMinutes * 60;
    }
}
