package com.benek.utils;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private static final String SECRET_KEY ="9HSyMEj1sV1+JTu8Xv7BvtFeBcPwRY097ZjDschU/bomUKJIJdB/wj8Jdhpw95F4p4VIUl8e7/RKcz2tO5mGjVB3FAtU7xUiCpc8EINaC24/mT/hKEf4CV8kwaM23x8zgNFP0UABw9FAoG4X1yt5CebPWBw96TuUTNgFq5kPWnsllgj48b0CBpVIaU8rpRJpDAjdREB5l3zOFcDsUaL8mUGouWT71Ml9LoIgH5VEwXEIxYjGhy9oqO7gaxs/S4Hls/wPsZ0QqbZkXc5pcwK4oPTEaUMuJ1S62OooiZxxpiD/jzq425aXgurU9KR5lQLgDOhyfFLpUYleDgMFLA8lQAD48xNOUsL8/rT03d0KsfUOKAlQE0RGQRJHcdbKPgpuz5En1D5xL6kEs9NYybZcw9KOM6ocnA4maox42kipGC67dmwL0kR1791PkZEMybh5OIA3x8H2SqPNDaFBGPRDu5+i83xcRxnE2FUSBhteHBwY+XEccZFl3sS0ITMr/v2OxwPdfTBtu/yRY1+PqKUUcrLSMwO50VWWT16DO0Jqx9VK/yG4jAs849+UoHfAhzwzsm0DSjgQGpygRqdCszz/Ly3UzJ0WLb0gBQBjcTJR/aOlZeKKh2vqJv+2e92U9IvGZwR1Yxy6m2xIHEo9M5rXSiXxnSqFIdVUzQF69w7bmuHLnA8DjXPh028miY7w44Ps\n";

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()))&&!isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
