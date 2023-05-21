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
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/admin/**","/course/**", "/admin", "/blog/post/add", "/blog/post/edit/**", "/blog/post/delete/*", "/english-level-test/questions").permitAll()
                .requestMatchers("/auth/login" ,"/auth/registration", "/error", "/verify").permitAll()
                .requestMatchers("/css/**", "/js/**", "/media/**").permitAll()
                .requestMatchers("/", "/image/*","/blog", "/blog/post/*").permitAll()
                .requestMatchers("/english-level-test/**").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/myaccount", true)
                .failureUrl("/auth/login?error");
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
