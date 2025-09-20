package com.supercell.gaming_store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CorsConfigurationSource corsConfigurationSource) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Static resources from React build (located at /static/build/)
                        .requestMatchers("/", "/index.html", "/favicon.ico", "/manifest.json", "/robots.txt").permitAll()
                        .requestMatchers("/logo192.png", "/logo512.png", "/asset-manifest.json").permitAll()
                        .requestMatchers("/static/**").permitAll() // React's JS/CSS assets
                        .requestMatchers("/*.js", "/*.css", "/*.png", "/*.jpg", "/*.gif", "/*.ico", "/*.json", "/*.txt").permitAll()

                        // React Router frontend routes
                        .requestMatchers("/login", "/register", "/games", "/games/**").permitAll()
                        .requestMatchers("/profile", "/cart", "/checkout", "/wallet", "/admin", "/about", "/contact", "/home").permitAll()

                        // Your existing GIF assets
                        .requestMatchers("/GIF_Assets/**").permitAll()

                        // Public API endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/games").permitAll() // View games is public

                        // Admin only endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/members").hasRole("ADMIN") // View all members requires admin

                        // User endpoints (authenticated users)
                        .requestMatchers("/api/members/profile").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/recharges").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/transactions/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}