package com.terok.demo.security;




import java.util.Date;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;



@Component
public class JwtTokenProvider {

    Logger logger = LogManager.getLogger();

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
    	
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        String token = 
        	Jwts.builder()
                .setSubject(userPrincipal.getId().toString())
        		//.setSubject(userPrincipal.getName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        
        return token;
    }

    
    public String getUserIdFromJWT(String token) {
    	Claims claims = Jwts.parser()
    			.setSigningKey(jwtSecret)
    			.parseClaimsJws(token)
    			.getBody();
    	
    	return claims.getSubject();
    }
    
    public String getUserNameFromJWT(String token) {
    	
    	String username = Jwts.parser().parseClaimsJws(token).getBody().getSubject(); 
       	logger.info("USER " + username);
    	
    	return username;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret)
            .parseClaimsJws(authToken)
            .getBody().getSubject();
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
