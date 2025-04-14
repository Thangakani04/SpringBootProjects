package com.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springsecurity.service.LoginService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class APISecurityConfig {
	
	@Autowired
	private ApiauthenticationEntrypoint authentrypoint;
	
	@Autowired
	private JWTAuthenticationFilter authfilter;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
				.authorizeHttpRequests()
				.antMatchers("/api/public/**").permitAll()
				.antMatchers("/api/auth/consumer/**").hasAnyAuthority("CONSUMER")
				.antMatchers("/api/auth/seller/**").hasAnyAuthority("SELLER")
				.anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().exceptionHandling().authenticationEntryPoint(authentrypoint)
				.and().addFilterBefore(authfilter, UsernamePasswordAuthenticationFilter.class).build();
		
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(loginService);
		provider.setPasswordEncoder(getPasswordEncoder());
		return provider;
		
	}
	
	@Bean
	public AuthenticationManager authManager() throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

}
