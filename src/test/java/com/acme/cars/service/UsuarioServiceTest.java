package com.acme.cars.service;

import com.acme.cars.exception.RegraDeNegocioException;
import com.acme.cars.model.Role;
import com.acme.cars.model.Usuario;
import com.acme.cars.payload.RegisterRequest;
import com.acme.cars.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;

    @Test
    @DisplayName("Deve registar um utilizador com sucesso")
    void registrarSucesso() {
        var request = new RegisterRequest();
        request.setEmail("novo@acme.com");
        request.setPassword("senha123");
        request.setNome("Novo User");
        request.setRole(Role.FUNCIONARIO);

        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("senha123")).thenReturn("hash_seguro");
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = service.registrar(request);

        assertNotNull(resultado);
        assertEquals("hash_seguro", resultado.getPassword());
        verify(repository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao registar email duplicado")
    void registrarErroEmailDuplicado() {
        var request = new RegisterRequest();
        request.setEmail("admin@acme.com");

        when(repository.findByEmail("admin@acme.com")).thenReturn(Optional.of(new Usuario()));

        assertThrows(RegraDeNegocioException.class, () -> service.registrar(request));
        verify(repository, never()).save(any());
    }
}