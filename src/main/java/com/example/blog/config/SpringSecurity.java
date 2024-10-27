package com.example.blog.config;

import com.example.blog.service.AppUserDetailsService;
import jakarta.servlet.SessionCookieConfig;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    private final JwtFilter jwtFilter;

    public SpringSecurity(AppUserDetailsService appUserDetailsService, JwtFilter jwtFilter) {
        this.appUserDetailsService = appUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http, ServletWebServerFactoryAutoConfiguration servletWebServerFactoryAutoConfiguration) throws Exception {

        return http.
                csrf( AbstractHttpConfigurer::disable )
                .authorizeHttpRequests( auth ->
                        auth.requestMatchers("/api/users/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/posts/**", "/api/comments/**", "api/users").hasAnyRole("USER", "ADMIN")
                        .requestMatchers( "api/users/register", "api/users/login" ).permitAll()
                        .anyRequest().authenticated() )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic( Customizer.withDefaults() )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService( appUserDetailsService );
        provider.setPasswordEncoder( new BCryptPasswordEncoder(12) );
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    
}
