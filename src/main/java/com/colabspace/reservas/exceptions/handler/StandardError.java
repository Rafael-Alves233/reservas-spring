package com.colabspace.reservas.exceptions.handler;

import java.time.LocalDateTime;

public record StandardError(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        String path
) {
}
