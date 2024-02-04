package com.gl.studentmanagementsystem.config;

import com.gl.studentmanagementsystem.accessdeniedhandler.CustomAccessDeniedHandler;
import com.gl.studentmanagementsystem.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/students/list/", "/students/create/**",
                                "/students/edit/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/students/delete/**", "/students/edit/**").hasAuthority("ADMIN")
                        .requestMatchers("/login", "/signup").permitAll()
                        .requestMatchers( "/favicon.svg").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/students/list")
                        .passwordParameter("password")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll())
                .userDetailsService(customUserDetailsService); // Set your custom UserDetailsService here
        http.exceptionHandling(accessDenied -> accessDenied
                .accessDeniedPage("/students/403"));
        return http.build();
    }
}
