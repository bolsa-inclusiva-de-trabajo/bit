package ar.org.cpci.bit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import ar.org.cpci.bit.shared.Utils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // http.authorizeRequests().antMatchers("/**").permitAll(); // sacar logueo

        http.formLogin()
            .loginPage("/login")
            .successHandler(getAuthSuccessHandler())
            .permitAll();
        http.logout()
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .permitAll();
        http.authorizeRequests()
            .antMatchers("/", "/static/**", "/international", "/error").permitAll()
            .antMatchers("/**").authenticated();
    }

    @Bean
    public AuthenticationSuccessHandler getAuthSuccessHandler() {
        return new AuthSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Utils.getPasswordEncoder();
    }

}
