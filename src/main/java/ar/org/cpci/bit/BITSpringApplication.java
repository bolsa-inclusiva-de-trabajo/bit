package ar.org.cpci.bit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class BITSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BITSpringApplication.class, args);
    }

}
