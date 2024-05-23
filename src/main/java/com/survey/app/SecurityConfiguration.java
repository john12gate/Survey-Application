package com.survey.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").hasRole("COORDINATOR")
                .antMatchers("/admin").hasRole("COORDINATOR")
                .antMatchers("/api/v1/question/**").hasRole("COORDINATOR") // Restrict access to QuestionController
                .antMatchers("/").permitAll()
                .and().formLogin()
                .and().csrf().ignoringAntMatchers("/**") // don't apply CSRF protection to /h2-console;
                .and().headers().frameOptions().sameOrigin(); // allow use of frame to same origin urls
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("jali")
                .password("npfmicrofinancebankplc")
                .roles("COORDINATOR")
                .and()
                .withUser("client")
                .password("client")
                .roles("RESPONDENT");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();    }
}
