package com.acme.cars;

import com.acme.cars.model.Usuario;
import com.acme.cars.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class CarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			List<Usuario> usuarios = usuarioRepository.findAll();

			for (Usuario u : usuarios) {
				String senhaCriptografada = passwordEncoder.encode("123456");
				u.setPassword(senhaCriptografada);
				usuarioRepository.save(u);
			}
			System.out.println("--- SENHAS RESETADAS PARA: 123456 ---");
		};
	}
}