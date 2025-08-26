package chat.cyber.service;

import chat.cyber.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.access.token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.token.expiration}")
    private long refreshTokenExpiration;

    @Value("${JWT_SECRET}")
    private String tokenSecret;

    public SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(tokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateAccessToken(User user){

        return Jwts.builder()
                .issuer("cyber-AccessToken")
                .subject(user.getEmail())
                .expiration(Date.from(Instant.now().plusSeconds(accessTokenExpiration)))
                .issuedAt(new Date())
                .id(UUID.randomUUID().toString())
                .signWith(getSignInKey())
                .compact();
    }

    public String generateRefreshToken(User user){

        return Jwts.builder()
                .issuer("cyber-RefreshToken")
                .subject(user.getEmail())
                .expiration(Date.from(Instant.now().plusSeconds(refreshTokenExpiration)))
                .issuedAt(new Date())
                .id(UUID.randomUUID().toString())
                .signWith(getSignInKey())
                .compact();

    }

    public boolean isTokenValid(String token){

        try {

            Claims claims = Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return !claims.getExpiration().before(new Date());

        } catch (JwtException e) {

             return false;

        }
    }

    public boolean isRefreshTokenValid(String token, User user){

        try {

            Claims claims = Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            if (!claims.getSubject().equals(user.getEmail())){

                return false;

            }

            return !claims.getExpiration().before(new Date());

        } catch (JwtException e) {

            return false;

        }
    }

    public Claims getClaims(String token){

        Claims payload = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return payload;

    }
}
