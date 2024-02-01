package com.example.wineyapi.config;

import com.example.wineyapi.security.JwtAccessDeniedHandler;
import com.example.wineyapi.security.JwtAuthenticationEntryPoint;
import com.example.wineyapi.security.JwtSecurityConfig;
import com.example.wineyapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**"
                        ,"/favicon.ico"
                        ,"/swagger-ui/**"
                        ,"/swagger-resources/**"
                        ,"/api-docs/**"
                        ,"/v3/api-docs/**"
                        ,"/health/**"
                        ,"/docs/**"
                )
                ;

    }
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()


                // enable h2-console
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()
                .authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/image/**").permitAll()
                // NOTE - 아래 예시처럼 구체적인 경로부터 명시해주어야 합니다.
                .antMatchers("/users/{userId}/wine-grade").authenticated()
                .antMatchers("/users/{userId}/wine-badges").authenticated()
                .antMatchers("/users/logout").authenticated()
                .antMatchers("/refresh").authenticated()
                .antMatchers(HttpMethod.DELETE, "/users/{userId}").authenticated()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/profile").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(jwtService));

    }
}
