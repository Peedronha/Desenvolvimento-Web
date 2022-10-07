package br.pucbr.exemplo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PetClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
	}

/*
* https://www.javadevjournal.com/spring-security/spring-security-login/
* https://www.baeldung.com/http-put-patch-difference-spring
* https://www.baeldung.com/exception-handling-for-rest-with-spring
* https://www.baeldung.com/spring-response-entity
* https://www.javadevjournal.com/spring-security/spring-security-cors-filter/

* */

}
