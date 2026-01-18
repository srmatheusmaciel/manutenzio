package com.acme.cars.repository;

import com.acme.cars.model.OrdemServico;
import com.acme.cars.model.StatusOrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    Optional<OrdemServico> findByCarroPlacaAndStatus(String placa, StatusOrdemServico status);
}