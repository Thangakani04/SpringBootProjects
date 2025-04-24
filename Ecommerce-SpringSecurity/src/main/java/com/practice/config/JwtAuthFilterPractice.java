package com.practice.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.practice.service.UserAuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilterPractice extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtilPractice jwtUtil;
	
	@Autowired
	private UserAuthService userAuth;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//String token = request.getHeader("JWT");
		String token = extracttoken(request);
		String username = null;
		if(token!=null) {
			username = jwtUtil.extractUsername(token);
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				jwtUtil.validateToken(token);
				
				UserDetails user = userAuth.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			}
			catch(IllegalArgumentException e) {
				System.out.println("Token validation failed" +  e.getMessage());
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

	
	private String extracttoken(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String bearertoken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearertoken) && bearertoken.startsWith("Bearer ")) {
			return bearertoken.substring(7);
		}
		return null;
	}

}
