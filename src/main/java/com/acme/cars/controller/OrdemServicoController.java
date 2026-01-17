package com.acme.cars.controller;

import com.acme.cars.model.OrdemServico;
import com.acme.cars.payload.OrdemServicoRequest;
import com.acme.cars.service.OrdemServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordens-servico")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    @PostMapping
    public ResponseEntity<OrdemServico> abrirOS(@RequestBody OrdemServicoRequest request) {
        OrdemServico os = ordemServicoService.abrirOrdemServico(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(os);
    }
}