package com.unosquare.acmelearning.security;

import com.unosquare.acmelearning.enumeration.USER_TYPE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception{
        authBuilder.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/register/").permitAll()
                .antMatchers("/api/instructor/**").hasAuthority(USER_TYPE.INSTRUCTOR.name())
                .antMatchers("/api/student/**").hasAuthority(USER_TYPE.STUDENT.name())
                .and().httpBasic();
    }
}
