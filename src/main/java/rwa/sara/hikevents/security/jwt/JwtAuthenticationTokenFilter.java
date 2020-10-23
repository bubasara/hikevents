package rwa.sara.hikevents.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import rwa.sara.hikevents.security.service.LoggedInUser;
import rwa.sara.hikevents.security.service.UserDetailServiceImpl;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	private static final Logger loggerErr = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
	private JwtTokenManager jwtTokenManager;
	private UserDetailServiceImpl userDetailService;

	@Autowired
	public void setUserDetailServiceImpl(UserDetailServiceImpl userDetailService) {
		this.userDetailService = userDetailService;
	}

	@Autowired
	public void setJwtTokenManager(JwtTokenManager jwtTokenManager) {
		this.jwtTokenManager = jwtTokenManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJwt(request);
			if (jwt != null && jwtTokenManager.validateJwtToken(jwt)) {
				String username = jwtTokenManager.getUserNameFromJwtToken(jwt);
				LoggedInUser userDetails = (LoggedInUser) userDetailService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			loggerErr.error("Can't set the user authentication -> Message: {}", e);
		}
		filterChain.doFilter(request, response);
	}

	private String getJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer "))
			return authHeader.replace("Bearer ", "");
		return null;
	}

}
