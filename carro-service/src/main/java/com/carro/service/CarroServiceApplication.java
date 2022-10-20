package com.carro.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // indicando que se habilite este servicio y se registre en el servidor de eureka
public class CarroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarroServiceApplication.class, args);
	}

}
