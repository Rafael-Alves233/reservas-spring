package com.colabspace.reservas.exceptions.handler;

import com.colabspace.reservas.exceptions.ConflitoDeEstadoException;

import com.colabspace.reservas.exceptions.RecursoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ManipuladorGlobalDeExcecoes {
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<StandardError> tratarNaoEncontrado(RecursoNaoEncontradoException e, HttpServletRequest request){
        return montar(HttpStatus.NOT_FOUND,e.getMessage(),request);
    }

    @ExceptionHandler(ConflitoDeEstadoException.class)
    public ResponseEntity<StandardError> tratarConflito(
            ConflitoDeEstadoException ex, HttpServletRequest request) {
        return montar(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacaoResponse> tratarValidacao(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErroValidacaoResponse.CampoInvalido> campos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ErroValidacaoResponse.CampoInvalido(
                        e.getField(), e.getDefaultMessage()))
                .toList();

        ErroValidacaoResponse corpo = new ErroValidacaoResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida",
                request.getRequestURI(),
                campos
        );

        return ResponseEntity.badRequest().body(corpo);
    }

    public ResponseEntity<StandardError> montar(HttpStatus status, String mensagem, HttpServletRequest request){
        StandardError corpo = new StandardError(
                LocalDateTime.now(),
                status.value(),
                mensagem,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(corpo);
    }
}
