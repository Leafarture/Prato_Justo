package com.TCC.Prato_Justo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable()) // desabilita CSRF para testes
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/**", "/css/**", "/js/**").permitAll() // libera cadastro, login e arquivos estáticos
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .loginPage("/login") // aqui você coloca o caminho da SUA página de login
                            .permitAll()
                    )
                    .logout(logout -> logout.permitAll());

            return http.build();
        }
    }


