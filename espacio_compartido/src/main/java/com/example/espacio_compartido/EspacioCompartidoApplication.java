package com.example.espacio_compartido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching 
@SpringBootApplication
public class EspacioCompartidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EspacioCompartidoApplication.class, args);
	}

}