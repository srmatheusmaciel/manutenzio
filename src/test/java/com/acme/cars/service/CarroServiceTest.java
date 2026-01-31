package com.acme.cars.service;

import com.acme.cars.exception.RegraDeNegocioException;
import com.acme.cars.model.Carro;
import com.acme.cars.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @Mock
    private CarroRepository repository;

    @InjectMocks
    private CarroService service;

    @Test
    void naoDeveSalvarCarroComPlacaExistente() {
        var carro = Carro.builder().placa("GOLF-2020").build();
        when(repository.findByPlaca("GOLF-2020")).thenReturn(Optional.of(carro));

        assertThrows(RegraDeNegocioException.class, () -> service.salvar(carro));
        verify(repository, never()).save(any());
    }
}