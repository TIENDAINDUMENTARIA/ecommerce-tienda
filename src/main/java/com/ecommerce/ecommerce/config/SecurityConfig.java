package com.ecommerce.ecommerce.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ecommerce.ecommerce.jwt.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/health/**").permitAll()
                  .requestMatchers("/auth/**").permitAll()
                  .requestMatchers("/actuator/**").permitAll()
                  .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                  .requestMatchers(HttpMethod.GET, "/category/**").permitAll()
                  .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                  .requestMatchers(HttpMethod.POST, "/product/**").hasAnyRole("ADMIN", "MODERATOR")
                  .requestMatchers(HttpMethod.PUT, "/product/**").hasAnyRole("ADMIN", "MODERATOR")
                  .requestMatchers(HttpMethod.DELETE, "/product/**").hasAnyRole("ADMIN", "MODERATOR")
                  .requestMatchers(HttpMethod.POST, "/category/**").hasAnyRole("ADMIN", "MODERATOR")
                  .requestMatchers(HttpMethod.PUT, "/category/**").hasAnyRole("ADMIN", "MODERATOR")
                  .requestMatchers(HttpMethod.DELETE, "/category/**").hasAnyRole("ADMIN", "MODERATOR")
                  .requestMatchers("/user/me").authenticated() // Permitir acceso autenticado a /user/me
                  .requestMatchers("/user/all").hasRole("ADMIN") // Restringir /user/all a ADMIN
                  .requestMatchers("/user/role").hasRole("ADMIN") //
                  .requestMatchers("/cart/**").authenticated()
                  .requestMatchers("/order/**").authenticated()
                  .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
