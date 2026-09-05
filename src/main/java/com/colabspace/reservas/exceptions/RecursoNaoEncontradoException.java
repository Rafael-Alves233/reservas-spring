package com.colabspace.reservas.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(Long id) {
        super("Recurso nao encontrado. Id: "+ id);
    }
}
