package ar.org.cpci.bit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // FIXME DOES NOT WORK, "STATIC" NOT VISIBLE

        http//.exceptionHandling()
            //.and()
            //.sessionManagement()
            //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            //.and()
            .formLogin().loginPage("/login").permitAll()
            .and().logout().permitAll()
            .and().authorizeRequests()
            .antMatchers("/error").permitAll()
            .antMatchers("/public/**").permitAll()
            .antMatchers("/test/**").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/**").permitAll();//.authenticated(); // FIXME
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
