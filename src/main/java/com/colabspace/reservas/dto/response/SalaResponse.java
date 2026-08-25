package com.colabspace.reservas.dto.response;

import com.colabspace.reservas.domain.Sala;
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
    public static SalaResponse from(Sala sala) {
        return new SalaResponse(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.getAndar(),
                sala.getAtiva(),
                Set.copyOf(sala.getRecursos())
        );
    }
}
