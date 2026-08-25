package com.colabspace.reservas.dto.response;

import com.colabspace.reservas.domain.enums.RecursoSala;

import java.util.Set;

public record SalaResponse(
        Long id,
        String nome,
        Integer capacidade,
        Integer andar,
        Boolean ativa,
        Set<RecursoSala> recursos
) {
}
