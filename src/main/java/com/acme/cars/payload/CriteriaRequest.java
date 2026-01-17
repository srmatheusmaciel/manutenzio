package com.acme.cars.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Builder
@Data
public class CriteriaRequest {
    Optional<String> modelo;
    Optional<String> fabricante;
}
