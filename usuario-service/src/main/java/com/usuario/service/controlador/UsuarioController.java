package com.usuario.service.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		List<Usuario> usuarios = usuarioService.getAll();
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}
	
	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros") // "carrosCB" hace referencia lo que configure en "config-data/usuario-service"
	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Carro> carros = usuarioService.getCarros(id);
		return ResponseEntity.ok(carros);
	}
	
	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos") // "motosCB" hace referencia lo que configure en "config-data/usuario-service"
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Moto> motos = usuarioService.getMotos(id);
		return ResponseEntity.ok(motos);
	}
	
	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro") // "carrosCB" hace referencia lo que configure en "config-data/usuario-service"
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId,@RequestBody Carro carro){
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
		return ResponseEntity.ok(nuevoCarro);
	} 
	
	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto") // "motosCB" hace referencia lo que configure en "config-data/usuario-service"
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int usuarioId,@RequestBody Moto moto){
		Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
		return ResponseEntity.ok(nuevaMoto);
	}
	
	@CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos") // "todosCB" hace referencia lo que configure en "config-data/usuario-service
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId){
		Map<String,Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}
	
	// ------------------------------------------------------- metodos Circuit Breaker ------------------------------------------------------- ((
	private ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable("usuarioId") int id, RuntimeException exception){
		return new ResponseEntity("El usuario: " + id + " tiene los carros en el taller", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Carro>> fallBackSaveCarro(@PathVariable("usuarioId") int id, @RequestBody Carro carro, RuntimeException exception){
		return new ResponseEntity("El usuario: " + id + " no tiene dinero para los carros", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Carro>> fallBackGetMoto(@PathVariable("usuarioId") int id, RuntimeException exception){
		return new ResponseEntity("El usuario: " + id + " tiene las motos en el taller", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Carro>> fallBackSaveMoto(@PathVariable("usuarioId") int id, @RequestBody Moto moto, RuntimeException exception){
		return new ResponseEntity("El usuario: " + id + " no tiene dinero para las motos", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Carro>> fallBackGetTodos(@PathVariable("usuarioId") int id, RuntimeException exception){
		return new ResponseEntity("El usuario: " + id + " tiene los vehiculos en el taller", HttpStatus.OK);
	}

}