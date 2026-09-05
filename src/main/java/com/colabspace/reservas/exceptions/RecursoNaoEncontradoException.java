package com.colabspace.reservas.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(recurso + ". Id: "+ id);
    }
}
