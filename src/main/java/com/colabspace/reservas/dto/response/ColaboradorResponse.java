package com.colabspace.reservas.dto.response;

import com.colabspace.reservas.domain.Colaborador;
import com.colabspace.reservas.domain.enums.Departamento;

public record ColaboradorResponse (
        Long id,
        String nome,
        String email,
        Departamento departamento
){
    public static ColaboradorResponse from(Colaborador colaborador){
        return new ColaboradorResponse(
                colaborador.getId(),
                colaborador.getNome(),
                colaborador.getEmail(),
                colaborador.getDepartamento()
        );
    }
}
