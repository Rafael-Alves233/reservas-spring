package com.colabspace.reservas.dto.request;

import com.colabspace.reservas.domain.enums.RecursoSala;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record SalaUpdateRequest (
        String nome,
        @Positive Integer capacidade,
        @Positive Integer andar,
        Boolean ativa,
        Set<RecursoSala> recursos
){
}
