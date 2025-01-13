package com.nazran.springboot3firebseauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtGenerator jwtGenerator;
//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getJWTfromRequest(request);
		if(token != null && jwtGenerator.validateToken(token)) {
			String username = jwtGenerator.getUsernameFromJWT(token);
			String userType = jwtGenerator.getUserTypeFromJWT(token);

			//UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

			// Create a new CustomAuthenticationToken with the user ID
			//CustomAuthenticationToken customToken = new CustomAuthenticationToken(userDetails, null, userDetails.getAuthorities(), Long.valueOf(userType));
			//customToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, null));


			//SecurityContextHolder.getContext().setAuthentication(customToken);
		}
		filterChain.doFilter(request, response);
	}


	private String getJWTfromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken!=null &&  bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		} else {
			return null;
		}
	}

}
