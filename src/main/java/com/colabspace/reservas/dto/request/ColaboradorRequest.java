package com.colabspace.reservas.dto.request;

import com.colabspace.reservas.domain.enums.Departamento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ColaboradorRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotNull Departamento departamento
) {
}
