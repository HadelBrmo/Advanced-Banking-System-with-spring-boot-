package com.example.Advances.Banking.System.config;

import com.example.Advances.Banking.System.nfr.security.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()  // فقط للتطوير

                        // 🏦 نقاط النهاية الخاصة بالزبائن
                        .requestMatchers("/api/customer/**").hasAnyRole("CUSTOMER", "TELLER", "MANAGER", "ADMIN")

                        // 👨‍💼 نقاط النهاية الخاصة بالموظفين (Tellers)
                        .requestMatchers("/api/teller/**").hasAnyRole("TELLER", "MANAGER", "ADMIN")

                        // 👔 نقاط النهاية الخاصة بالمدراء
                        .requestMatchers("/api/manager/**").hasAnyRole("MANAGER", "ADMIN")

                        // ⭐ نقاط النهاية الخاصة بالإدارة
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 🛡️ أي طلب آخر يحتاج مصادقة
                        .anyRequest().authenticated()
                )

                // 🔄 تكوين سياسة الجلسات (بدون حالة مع JWT)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // TODO: إضافة JWT filter لاحقاً
                // .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}