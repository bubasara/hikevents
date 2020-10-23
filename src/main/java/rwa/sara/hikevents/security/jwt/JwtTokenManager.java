package rwa.sara.hikevents.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import rwa.sara.hikevents.security.service.LoggedInUser;

public class JwtTokenManager {

	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenManager.class);
	@Value("${sara.hikevents.jwtSecret}")
	private String jwtSecret;

	@Value("${sara.hikevents.jwtExpiration}")
	private int jwtExpiration;

	public String buildJwtToken(Authentication authentication) {

		LoggedInUser loggedInUser = (LoggedInUser) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((loggedInUser.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration * 100000))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token -> Message: {}", e);
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token -> Message: {}", e);
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token -> Message: {}", e);
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature -> Message: {} ", e);
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty -> Message: {}", e);
		}
		return false;
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

}
