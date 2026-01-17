package com.acme.cars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordem_servico")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    @Column(nullable = false)
    private String descricaoProblema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrdemServico status;

    @ManyToOne
    @JoinColumn(name = "carro_id", nullable = false)
    private Carro carro;
}