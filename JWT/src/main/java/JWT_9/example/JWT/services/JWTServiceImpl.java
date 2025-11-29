package JWT_9.example.JWT.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

//    public String generateToken(UserDetails userDetails) {
//        return Jwts.builder().setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
//                .signWith(getSigninKey(), SignatureAlgorithm.ES256)
//                .compact();
//    }
        public String generateToken(UserDetails userDetails) {
            // Assuming getSigninKey() returns the PrivateKey instance
            Key signinKey = getSigninKey();
        
            // Define the expiration time (24 hours is most likely the intended value)
            long expirationMillis = System.currentTimeMillis() + 1000L * 60 * 60 * 24; // 24 hours
        
            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(expirationMillis))
                    // The modern, non-deprecated way to sign:
                    // The Jwts.SIG class holds a reference to the latest recommended signature algorithms
                    .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

    public String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
//                .signWith(getSigninKey(),Jwts.SIG.HS256)
                .compact();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims =extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private Key getSigninKey() {
//        7877838278E329392F387I39972387877838278E329392F387I3997238O5BUY7F3
        byte[] key= Decoders.BASE64.decode("Ihdfndkdfidfqiwejfoqwiejfoqijwer091239r120j0294044S4");
        return  Keys.hmacShaKeyFor(key);
    }

    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }
}
