package com.acme.cars.service;

import com.acme.cars.model.Carro;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService {
    private final CarroService carroService;

    public void generate(Writer writer) {
        List<Carro> all = carroService.listarTodos();
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(new String[]{"ID", "PLACA", "MODELO", "ANO", "COR", "FABRICANTE", "STATUS"});
            for (Carro carro : all) {
                csvWriter.writeNext(new String[]{
                        String.valueOf(carro.getId()),
                        carro.getPlaca(),
                        carro.getModelo(),
                        String.valueOf(carro.getAno()),
                        carro.getCor(),
                        carro.getFabricante(),
                        carro.getStatus().name()
                });
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar CSV", e);
        }
    }
}
