package com.terok.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.terok.demo.services.MongoUserDetailsService;

//public class JwtAuthenticationFilter {

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private MongoUserDetailsService userDetailsService;
   

    Logger logger = LogManager.getLogger();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("ENSI ERQUEST ON " + request.toString());
        logger.info("PROTCOL ON " + request.getProtocol()); 	
    	try {
        	logger.info("ATTRIBUUTIT "  + request.getAttributeNames());
        	logger.info("REQUEST " + request.toString());
        	logger.info("HEADERIT " +request.getHeaderNames());
        	logger.info("request on " + request.getHeader("Authorization"));
            String jwt = getJwtFromRequest(request);
            logger.info("JWT ON " + jwt.toString());
            //logger.info("USERI " + tokenProvider.getUserNameFromJWT(jwt));
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                //Long userId = tokenProvider.getUserIdFromJWT(jwt);
            	String userId = tokenProvider.getUserIdFromJWT(jwt);
            	
            	logger.info("ONKO OBJECTID " + ObjectId.isValid(userId));
            	ObjectId oId = new ObjectId(userId);
            	logger.info("VALIDOITU TOKEN");
            	//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            	//String userName = auth.getName();
                //String userName = tokenProvider.getUserNameFromJWT(jwt);
                //logger.info("USERI IS" + userName);
                //logger.info("USER IS " + userName);
                //TODO MAKE JWT BY USERNAME
                /*
                    Note that you could also encode the user's username and roles inside JWT claims
                    and create the UserDetails object by parsing those claims from the JWT.
                    That would avoid the following database hit. It's completely up to you.
                 */
                UserDetails userDetails = userDetailsService.loadUserById(oId);
                //UserDetails userDetails = userDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = 
                		new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(authentication.getCredentials());
                logger.info(authentication.toString());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
        	logger.info("EI VOITY");
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.info("bearerTOken " + bearerToken);
        logger.info("sUTILS ON " + StringUtils.hasText(bearerToken));
        logger.info("STARTS WITH " + bearerToken.startsWith("Bearer"));
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        	logger.info("JWTFROM REQ " + bearerToken.substring(7, bearerToken.length()));
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
