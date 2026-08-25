package com.colabspace.reservas.dto.request;

import com.colabspace.reservas.domain.enums.RecursoSala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record SalaRequest(
        @NotBlank String nome,
        @NotNull @Positive  Integer capacidade,
        @NotNull @Positive Integer andar,
        Set<RecursoSala> recursos
) {
    public SalaRequest {
        recursos = (recursos == null) ? Set.of() : Set.copyOf(recursos);
    }
}
