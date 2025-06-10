package com.example.espacio_compartido.config;

import com.example.espacio_compartido.security.JwtAuthenticationEntryPoint;
import com.example.espacio_compartido.security.JwtAuthenticationFilter;
import com.example.espacio_compartido.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(corsConfigurationSource()).and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeHttpRequests()
                // Rutas públicas
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/error").permitAll()
                
                // Rutas para ENCARGADO (solo consultas y reservas)
                .requestMatchers(HttpMethod.GET, "/api/espacio/**").hasAnyRole("ADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.GET, "/api/categoria/**").hasAnyRole("ADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.GET, "/api/equipamientos/**").hasAnyRole("ADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.GET, "/api/reservador/**").hasAnyRole("ADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.GET,"/api/espacio-equipamiento/**").hasAnyRole("ADMIN", "ENCARGADO")
                // CRUD completo de reservas para ENCARGADO
                .requestMatchers("/api/reserva/**").hasAnyRole("ADMIN", "ENCARGADO")
                .requestMatchers("/api/reservador/**").hasAnyRole("ADMIN", "ENCARGADO")
                
                
                // Todo lo demás solo para ADMIN
                .anyRequest().hasRole("ADMIN");

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    
    // Especificar explícitamente los encabezados permitidos
    configuration.setAllowedHeaders(Arrays.asList(
        "Authorization", 
        "Content-Type", 
        "Accept", 
        "Origin", 
        "X-Requested-With",
        "Access-Control-Request-Method", 
        "Access-Control-Request-Headers"
    ));
    
    // Permitir que el navegador exponga estos encabezados al frontend
    configuration.setExposedHeaders(Arrays.asList(
        "Authorization",
        "Content-Disposition"
    ));
    
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L); // 1 hora en segundos
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
}