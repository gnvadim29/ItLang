package com.itlang.config;

import com.itlang.security.MyAuthenticationSuccessHandler;
import com.itlang.services.PersonDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
/**
 * @author Vadym Hnatiuk
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
    public class SecurityConfig{
        private final PersonDetailsService personDetailsService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/course/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/auth/login" ,"/auth/registration", "/error", "/verify", "/auth/resetPassword", "/reset", "/auth/changePassword").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/media/**", "/about").permitAll()
                    .requestMatchers("/", "/image/*","/blog", "/blog/post/*").permitAll()
                    .requestMatchers("/english-level-test/**").permitAll()
                    .anyRequest().hasAnyRole("USER", "ADMIN")
                    .and()
                    .formLogin().loginPage("/auth/login")
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/myaccount", true)
                    .failureUrl("/auth/login?error")
                    .and()
                    .logout().logoutUrl("/logout")
                    .permitAll();
            return http.build();
        }

        @Bean
        public DaoAuthenticationProvider authProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(personDetailsService);
            authProvider.setPasswordEncoder(encoder());
            return authProvider;
        }

        @Bean
        public PasswordEncoder encoder() {
            return new BCryptPasswordEncoder();
        }

    }
