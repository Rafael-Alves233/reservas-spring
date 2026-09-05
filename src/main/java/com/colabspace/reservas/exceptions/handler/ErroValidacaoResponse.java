package com.colabspace.reservas.exceptions.handler;

import java.time.LocalDateTime;
import java.util.List;

public record ErroValidacaoResponse(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        String caminho,
        List<CampoInvalido> campos
) {
    public record CampoInvalido(String campo, String mensagem) {}
}
