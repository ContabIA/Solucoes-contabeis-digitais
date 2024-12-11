package com.contabia.contabia.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.contabia.contabia.services.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private FilterSecurity filterSecurity;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf((csrf) -> csrf
                    .disable()
                )

                .sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers("/styles/**", "/js/**", "/img/**").permitAll()
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/cadastro").permitAll()
                    .anyRequest().authenticated()
                )   

                .addFilterBefore(filterSecurity, UsernamePasswordAuthenticationFilter.class)

                .logout((logout) -> logout
                    .deleteCookies("Authorization")
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                )

                .exceptionHandling((ex) -> ex
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.sendRedirect("/login");
                    })
                )

                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsServiceImp userDetailsServiceImp, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImp);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
