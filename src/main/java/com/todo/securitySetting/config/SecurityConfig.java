package com.todo.securitySetting.config;

import com.todo.securitySetting.Handler.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        //.requestMatchers("/admin/**").hasAuthority(Roles.ADMIN.getValue())
                        .requestMatchers("/resources/**", "/css/**", "/images/**", "/js/**", "/messages",
                                "/login/**", "/main/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login/loginForm")
                        .loginProcessingUrl("/login/login")
                        .defaultSuccessUrl("/main/dashboard")
                        .successHandler(new LoginSuccessHandler())
                        .usernameParameter("userid")
                        .passwordParameter("password")

                )
                .logout(logout -> logout
                        .logoutUrl("/login/logout")
                        .logoutSuccessUrl("/login/loginForm")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

}
