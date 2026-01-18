package com.acme.cars.service;

import com.acme.cars.exception.RegraDeNegocioException;
import com.acme.cars.model.Carro;
import com.acme.cars.model.OrdemServico;
import com.acme.cars.model.StatusCarro;
import com.acme.cars.model.StatusOrdemServico;
import com.acme.cars.payload.OrdemServicoRequest;
import com.acme.cars.repository.CarroRepository;
import com.acme.cars.repository.OrdemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;
    private final CarroService carroService;
    private final CarroRepository carroRepository;

    @Transactional
    public OrdemServico abrirOrdemServico(OrdemServicoRequest request) {
        Carro carro = carroService.buscarPorPlaca(request.placa());

        if (carro.getStatus() != StatusCarro.DISPONIVEL) {
            throw new RegraDeNegocioException("Carro não está disponível para nova OS. Status atual: " + carro.getStatus());
        }

        carro.setStatus(StatusCarro.EM_MANUTENCAO);
        carroRepository.save(carro);

        OrdemServico os = OrdemServico.builder()
                .carro(carro)
                .descricaoProblema(request.descricao())
                .dataAbertura(LocalDateTime.now())
                .status(StatusOrdemServico.ABERTA)
                .build();

        return ordemServicoRepository.save(os);
    }

    @Transactional
    public void finalizarOrdemServico(Long id) {
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new com.acme.cars.exception.RecursoNaoEncontradoException("Ordem de Serviço não encontrada com id: " + id));

        if (os.getStatus() != StatusOrdemServico.ABERTA) {
            throw new RegraDeNegocioException("Esta OS não pode ser finalizada pois está com status: " + os.getStatus());
        }

        os.setStatus(StatusOrdemServico.FINALIZADA);
        os.setDataFechamento(LocalDateTime.now());

        Carro carro = os.getCarro();
        carro.setStatus(StatusCarro.DISPONIVEL);

        carroRepository.save(carro);
        ordemServicoRepository.save(os);
    }

    public OrdemServico buscarAbertaPorPlaca(String placa) {
        return ordemServicoRepository.findByCarroPlacaAndStatus(placa, StatusOrdemServico.ABERTA)
                .orElseThrow(() -> new com.acme.cars.exception.RecursoNaoEncontradoException("Nenhuma OS aberta para o veículo " + placa));
    }
}