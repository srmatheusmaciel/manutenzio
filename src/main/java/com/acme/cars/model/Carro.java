package com.acme.cars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carro")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String placa;

    private String modelo;
    private int ano;
    private String cor;
    private String fabricante;

    @Enumerated(EnumType.STRING)
    private StatusCarro status = StatusCarro.DISPONIVEL;
}