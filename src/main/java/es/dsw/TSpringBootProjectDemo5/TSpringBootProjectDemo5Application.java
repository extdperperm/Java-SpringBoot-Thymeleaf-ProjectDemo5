package es.dsw.TSpringBootProjectDemo5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication 
@ComponentScan(basePackages = "es.dsw")
public class TSpringBootProjectDemo5Application {

	public static void main(String[] args) {
		SpringApplication.run(TSpringBootProjectDemo5Application.class, args);
	}

}
