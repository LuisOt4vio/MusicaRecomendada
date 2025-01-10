package bcc.ifsuldeminas.sistemaMusicas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemaMusicasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaMusicasApplication.class, args);
	}

}
