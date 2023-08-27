package com.example.wineyapi.config;

import com.example.wineyapi.security.JwtSecurityConfig;
import com.example.wineyapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
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
                        ,"/users/**" // TODO : 인가처리시 제외하기
                        ,"/health/**"
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

                .and()
                .authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/image/**").permitAll()
                .antMatchers("/users/refresh").permitAll()
                .antMatchers("/profile").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(jwtService));

    }
}
