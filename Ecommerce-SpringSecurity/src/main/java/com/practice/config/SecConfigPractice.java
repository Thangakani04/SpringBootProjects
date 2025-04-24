package com.practice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.practice.service.UserAuthService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Enables method-level security annotations (@PreAuthorize, etc.)
public class SecConfigPractice {

    @Autowired
    private UserAuthService userService;

    @Autowired
    private ApiAuthEntryPoint apiEntryPoint;

    @Autowired
    private JwtAuthFilterPractice jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                    .requestMatchers("/api/auth/public/**", "/h2-console/**").permitAll()  // Allow public and H2 console access
                    .requestMatchers("/api/auth/consumer/**").hasAuthority("CONSUMER")  // Restricted to CONSUMER role
                    .requestMatchers("/api/auth/seller/**").hasAuthority("SELLER")  // Restricted to SELLER role
                    .anyRequest().authenticated()  // All other requests must be authenticated
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session (for JWT)
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(apiEntryPoint)  // Custom entry point for authentication errors
                .and()
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)  // Add JWT filter before the UsernamePasswordAuthenticationFilter
                .build();
    }

    // AuthenticationManager bean setup
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());  // Use the password encoder
        return authenticationManagerBuilder.build();
    }

    // Use a secure password encoder like BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCryptPasswordEncoder in production for security
    }

    // AuthenticationProvider bean (DAO authentication provider)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());  // Use the secure password encoder
        return provider;
    }
}
