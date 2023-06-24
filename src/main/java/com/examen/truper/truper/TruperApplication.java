package com.examen.truper.truper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TruperApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruperApplication.class, args);
	}

}
