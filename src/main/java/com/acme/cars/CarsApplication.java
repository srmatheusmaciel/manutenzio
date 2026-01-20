package com.acme.cars;

import com.acme.cars.model.Role; // Verifique se sua Enum chama Role ou similar
import com.acme.cars.model.Usuario;
import com.acme.cars.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Usuario admin = usuarioRepository.findByEmail("admin@acme.com")
					.orElse(new Usuario());

			admin.setNome("Admin Docker");
			admin.setEmail("admin@acme.com");
			admin.setPassword(passwordEncoder.encode("123456"));
			admin.setRole(Role.ADMIN);

			if (admin.getAvatar() == null || admin.getAvatar().isEmpty()) {
				admin.setAvatar("https://github.com/shadcn.png");
			}

			usuarioRepository.save(admin);
			System.out.println("✅ USUÁRIO ADMIN SINCRONIZADO: admin@acme.com / 123456");
		};
	}
}