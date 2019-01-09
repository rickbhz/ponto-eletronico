package com.cadu.pontoeletronico.api.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // For example: Use only Http Basic and not form login.
    	 http.antMatcher("/api/**")
         .authorizeRequests()
             .antMatchers("/api/**").permitAll()
             .anyRequest().authenticated();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
           .antMatchers("/api/**"); 
    }    
}
