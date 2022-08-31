package com.spring.usermanagement.security.jwt;

import com.spring.usermanagement.security.service.UserDetailsImpls;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(JwtUtils.class);
    @Value("${usermanagement.jwtSecret}")
    private String jwtSecret;
    @Value("${usermanagement.jwtExpirationMs}")
    private String jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpls userDetailsImpls = (UserDetailsImpls) authentication.getPrincipal();
        return Jwts.builder().setSubject(userDetailsImpls.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + Long.parseLong(jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();

    }

    public boolean validToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT Signature:{}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token:{}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired:{}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported:{}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty:{}", e.getMessage());
        }
        return false;
    }

    public Claims parseJwt(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
