package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signup","/login","/css/**","/js/**", "/h2/**")
                .permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.formLogin()
                .defaultSuccessUrl("/",true);

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
