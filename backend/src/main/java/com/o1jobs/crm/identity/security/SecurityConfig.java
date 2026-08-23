package com.o1jobs.crm.identity.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/v1/users").hasAuthority("ADMIN")

                        // clients: MANAGER pełny CRUD; RECRUITER i PARTNER tylko odczyt
                        .requestMatchers(HttpMethod.GET, "/api/v1/clients/**")
                        .hasAnyAuthority("ADMIN", "MANAGER", "RECRUITER", "PARTNER")
                        .requestMatchers("/api/v1/clients/**")
                        .hasAnyAuthority("ADMIN", "MANAGER")

                        // caregivers: MANAGER + RECRUITER pełny CRUD; PARTNER tylko odczyt
                        .requestMatchers(HttpMethod.GET, "/api/v1/caregivers/**")
                        .hasAnyAuthority("ADMIN", "MANAGER", "RECRUITER", "PARTNER")
                        .requestMatchers("/api/v1/caregivers/**")
                        .hasAnyAuthority("ADMIN", "MANAGER", "RECRUITER")

                        // care-recipients: MANAGER + RECRUITER pełny CRUD; PARTNER tylko odczyt
                        .requestMatchers(HttpMethod.GET, "/api/v1/care-recipients/**")
                        .hasAnyAuthority("ADMIN", "MANAGER", "RECRUITER", "PARTNER")
                        .requestMatchers("/api/v1/care-recipients/**")
                        .hasAnyAuthority("ADMIN", "MANAGER", "RECRUITER")

                        // assignments: MANAGER + RECRUITER pełny CRUD; PARTNER tylko odczyt
                        .requestMatchers(HttpMethod.GET, "/api/v1/assignments/**")
                        .hasAnyAuthority("ADMIN", "MANAGER", "RECRUITER", "PARTNER")
                        .requestMatchers("/api/v1/assignments/**")
                        .hasAnyAuthority("ADMIN", "MANAGER", "RECRUITER")

                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "https://o1jobs-crm-1.onrender.com"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}