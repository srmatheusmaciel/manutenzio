package com.acme.cars.controller;

import com.acme.cars.model.Carro;
import com.acme.cars.payload.CarroRequest;
import com.acme.cars.payload.CriteriaRequest;
import com.acme.cars.service.CarroService;
import com.acme.cars.service.CsvService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarroController {

    private final CarroService carroService;
    private final CsvService csvService;

    @GetMapping
    public ResponseEntity<List<Carro>> getAllCarros(@RequestParam(required = false) String placa) {
        return ResponseEntity.ok(carroService.listarComFiltro(placa));
    }

    @PostMapping
    public ResponseEntity<Carro> createCarro(@Valid @RequestBody CarroRequest carro) {
        Carro novoCarro = carroService.salvar(carro.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCarro);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Carro>> search(
            @RequestHeader(value = "modelo", required = false) Optional<String> modelo,
            @RequestHeader(value = "fabricante", required = false) Optional<String> fabricante) {

        CriteriaRequest criteriaRequest = CriteriaRequest.builder()
                .modelo(modelo)
                .fabricante(fabricante)
                .build();
        List<Carro> search = carroService.search(criteriaRequest);
        return ResponseEntity.ok(search);
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Carro> buscarPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(carroService.buscarPorPlaca(placa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> buscarPorId(@PathVariable Long id) {
        Carro carro = carroService.buscarPorId(id);
        return ResponseEntity.ok(carro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizar(@PathVariable Long id, @RequestBody Carro carroAtualizado) {
        Carro carro = carroService.atualizar(id, carroAtualizado);
        return ResponseEntity.ok(carro);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        carroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export-cars")
    public void exportCharacters(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=carros.csv");

        csvService.generate(response.getWriter());
    }
}