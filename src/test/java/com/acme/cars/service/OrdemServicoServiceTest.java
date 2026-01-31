package com.acme.cars.service;

import com.acme.cars.exception.RegraDeNegocioException;
import com.acme.cars.model.Carro;
import com.acme.cars.model.OrdemServico;
import com.acme.cars.model.StatusCarro;
import com.acme.cars.payload.OrdemServicoRequest;
import com.acme.cars.repository.CarroRepository;
import com.acme.cars.repository.OrdemServicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemServicoServiceTest {

    @Mock
    private OrdemServicoRepository repository;
    @Mock
    private CarroService carroService;
    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private OrdemServicoService service;

    @Test
    @DisplayName("Deve abrir OS com sucesso e mudar status do carro")
    void abrirOrdemServicoSucesso() {
        var request = new OrdemServicoRequest("ABC-1234", "Troca de óleo");
        var carro = Carro.builder().placa("ABC-1234").status(StatusCarro.DISPONIVEL).build();

        when(carroService.buscarPorPlaca("ABC-1234")).thenReturn(carro);
        when(repository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        OrdemServico os = service.abrirOrdemServico(request);

        assertEquals(StatusCarro.EM_MANUTENCAO, carro.getStatus());
        assertNotNull(os.getDataAbertura());
        verify(carroRepository, times(1)).save(carro);
    }

    @Test
    @DisplayName("Deve lançar exceção se carro já estiver em manutenção")
    void abrirOSErroCarroIndisponivel() {
        var request = new OrdemServicoRequest("ABC-1234", "Revisão");
        var carro = Carro.builder().status(StatusCarro.EM_MANUTENCAO).build();

        when(carroService.buscarPorPlaca("ABC-1234")).thenReturn(carro);

        assertThrows(RegraDeNegocioException.class, () -> service.abrirOrdemServico(request));
    }
}