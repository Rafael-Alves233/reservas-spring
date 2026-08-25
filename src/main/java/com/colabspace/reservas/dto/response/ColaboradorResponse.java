package com.colabspace.reservas.dto.response;

import com.colabspace.reservas.domain.enums.Departamento;

public record ColaboradorResponse (
        Long id,
        String nome,
        String email,
        Departamento departamento
){
}
