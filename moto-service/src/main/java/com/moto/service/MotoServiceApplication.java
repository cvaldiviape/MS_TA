package com.moto.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // indicando que se habilite este servicio y se registre en el servidor de eureka
public class MotoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotoServiceApplication.class, args);
	}

}
