package com.acme.cars.service;

import com.acme.cars.exception.RegraDeNegocioException;
import com.acme.cars.model.Usuario;
import com.acme.cars.payload.RegisterRequest;
import com.acme.cars.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario registrar(RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegraDeNegocioException("Email já cadastrado.");
        }

        Usuario novoUsuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .avatar("https://github.com/shadcn.png")
                .build();

        return usuarioRepository.save(novoUsuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            // Criamos uma representação sem senha para retorno,
            // evitando que o Hibernate limpe a senha no DB por "dirty checking"
            Usuario dto = new Usuario();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setRole(usuario.getRole());
            dto.setAvatar(usuario.getAvatar());
            dto.setPassword(null);
            return dto;
        });
    }
}