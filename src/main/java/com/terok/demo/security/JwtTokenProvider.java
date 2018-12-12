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
    	
    	//logger.info(authentication.getPrincipal())
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        logger.info(userPrincipal.getName());
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        String token = 
        	Jwts.builder()
                //.setSubject(userPrincipal.getId().toString())
        		.setSubject(userPrincipal.getName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        logger.info("TOKEN LUOTU " + token);
        
        return token;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
    
    public String getUserNameFromJWT(String token) {
    	logger.info("TOKEN on " + token);
    	
    	//TODO PARSE 
    	//Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    	String username = Jwts.parser()
    			.setSigningKey(jwtSecret)
    			.parseClaimsJws(token)
    			.getBody().getSubject();
    	logger.info("USERI SAADAAN " + username);
    	/*Claims claims = Jwts.parser()
    			.setSigningKey(jwtSecret)
    			.parseClaimsJwt(token)
    			.getBody();
    	logger.info("Claims on " + claims);
    	*/
    	//return claims.toString();
    	return username;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
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
