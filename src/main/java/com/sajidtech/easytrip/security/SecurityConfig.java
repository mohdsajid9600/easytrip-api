package com.sajidtech.easytrip.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws AuthenticationException, AccessDeniedException, Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // public APIs
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // role based APIs
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/drivers/**","/booking/driver/**","/cab/driver/**").hasRole("DRIVER")
                        .requestMatchers("/customers/**","/booking/customer/**","/cab/search/available").hasRole("CUSTOMER")

                        // everything else must be authenticated
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .securityContext(security -> security
                        .requireExplicitSave(false)
                )
                .formLogin(form -> form.disable())
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)   // session destroy
                        .clearAuthentication(true)     // authentication clear
                        .deleteCookies("JSESSIONID")   // cookie remove
                        .logoutSuccessHandler((request, response, authentication) -> {

                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");

                            String json = """
                                        {
                                          "success": true,
                                          "message": "Logout successful"
                                        }
                                    """;

                            response.getWriter().write(json);
                        })
                        .permitAll()
                );
//                .httpBasic(Customizer.withDefaults());// simple login (no JWT)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
