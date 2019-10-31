package com.tigratius.employeedatastorage.config;

import com.tigratius.employeedatastorage.security.jwt.JwtConfigurer;
import com.tigratius.employeedatastorage.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String AUTH_ENDPOINT = "/api/v1/auth/**";
    private static final String USERS_ENDPOINT = "/api/v1/users/**";
    private static final String EMPLOYEE_ENDPOINT = "/api/v1/employees/**";
    private static final String DEPARTMENTS_ENDPOINT = "/api/v1/departments/**";

    @Autowired
    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT).permitAll()
                //-----------------
                .antMatchers(HttpMethod.GET, USERS_ENDPOINT).hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, USERS_ENDPOINT).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, USERS_ENDPOINT).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, USERS_ENDPOINT).hasRole("ADMIN")
                //-----------------
                .antMatchers(HttpMethod.GET, EMPLOYEE_ENDPOINT).hasRole("USER")
                .antMatchers(HttpMethod.POST, EMPLOYEE_ENDPOINT).hasRole("MODERATOR")
                .antMatchers(HttpMethod.PUT, EMPLOYEE_ENDPOINT).hasRole("MODERATOR")
                .antMatchers(HttpMethod.DELETE, EMPLOYEE_ENDPOINT).hasRole("MODERATOR")
                //-----------------
                .antMatchers(HttpMethod.GET, DEPARTMENTS_ENDPOINT).hasRole("USER")
                .antMatchers(HttpMethod.POST, DEPARTMENTS_ENDPOINT).hasRole("MODERATOR")
                .antMatchers(HttpMethod.PUT, DEPARTMENTS_ENDPOINT).hasRole("MODERATOR")
                .antMatchers(HttpMethod.DELETE, DEPARTMENTS_ENDPOINT).hasRole("MODERATOR")
                //-----------------
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
