package com.cns.project_management.security;

import com.cns.project_management.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private AuthenticationService authenticationService;


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthorizationFilter authorizationFilter() throws Exception {
        //AuthorizationFilter authorizationFilter = new AuthorizationFilter(this.authenticationManagerBean(), userJpaRepository, authenticationService);
        com.cns.project_management.security.AuthorizationFilter authorizationFilter = new com.cns.project_management.security.AuthorizationFilter(authenticationManagerBean(), userJpaRepository, authenticationService);
        return authorizationFilter;
    }

   /*@Override
    public void configure(WebSecurity web) throws Exception {
        // TODO Auto-generated method stub
        //auth.userDetailsService(userDetailsService);
        web.ignoring().antMatchers("/registerUser");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        http
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/private/user/**").hasAuthority("user")
                .antMatchers("/private/project/**").hasAuthority("user")
                .antMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and().csrf().disable();
        http.addFilterAt(authorizationFilter(), BasicAuthenticationFilter.class);
    }
}
