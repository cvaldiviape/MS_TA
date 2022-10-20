package com.usuario.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients // Esto anotacion permite habilita al cliente "feign", ademas de indica
					// que esta clase sera el cliente "feign", en resumen, desde este servicio
					// podre llamar a los demas servicios.
@SpringBootApplication
@EnableEurekaClient // indicando que se habilite este servicio y se registre en el servidor de eureka
public class UsuarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioServiceApplication.class, args);
	}

}
