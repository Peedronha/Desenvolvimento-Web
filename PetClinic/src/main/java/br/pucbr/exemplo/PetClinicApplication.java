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

* 		Banco é o H2 então não precisa instalar nada.
		Lembra de transformar o pom.xml em maven.
*
* 		Se quiser testar a aplicação o swagger é esse:  http://localhost:8082/
		(Se algum teste retorna 403 - forbidden é pq a segurança da funcionando se retornar outra coisa pode )
		Por aqui vc consegue fazer os métodos que tão no controller
		Tem dois serviços que eu criei, um para usuário e outro que faz clinica funcionar

		a unica coisa que eu não fiz nessa versão do código é a inserção em tabelas que possuem uma chave estrangeira e são uma lista.
* */

}
