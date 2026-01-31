package com.acme.cars.payload;

import com.acme.cars.model.Carro;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CarroRequest(
        @NotBlank(message = "A placa é obrigatória")
        @Pattern(regexp = "[A-Z]{3}-[0-9]{4}|[A-Z]{3}[0-9][A-Z][0-9]{2}", message = "Placa em formato inválido")
        String placa,

        @NotBlank(message = "O modelo é obrigatório")
        String modelo,

        @Min(value = 1886, message = "Ano inválido")
        @Max(value = 2026, message = "Ano não pode ser no futuro")
        int ano,

        @NotBlank(message = "A cor é obrigatória")
        String cor,

        @NotBlank(message = "O fabricante é obrigatório")
        String fabricante
) {
    public Carro toModel() {
        return Carro.builder()
                .placa(this.placa)
                .modelo(this.modelo)
                .ano(this.ano)
                .cor(this.cor)
                .fabricante(this.fabricante)
                .build();
    }
}