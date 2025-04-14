package com.springsecurity.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springsecurity.service.LoginService;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWT_Util jWT_Util;
	
	@Autowired
	private LoginService loginService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader("JWT");
		String username = null;
		if(token !=null) {
			username = jWT_Util.getUsernamefromToken(token);
		}
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				UserDetails userdetails = loginService.loadUserByUsername(username);
				jWT_Util.validToken(token, userdetails);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			catch(IllegalArgumentException e) {
				System.out.println("Token validation failed " + e.getMessage());
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
