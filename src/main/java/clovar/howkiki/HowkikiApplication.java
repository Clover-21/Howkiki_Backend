package clovar.howkiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HowkikiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HowkikiApplication.class, args);
	}

}
