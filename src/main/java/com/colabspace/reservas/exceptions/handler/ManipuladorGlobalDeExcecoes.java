package com.colabspace.reservas.exceptions.handler;

import com.colabspace.reservas.exceptions.ConflitoDeEstadoException;

import com.colabspace.reservas.exceptions.RecursoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> tratarMalformado(HttpMessageNotReadableException ex,
                                                          HttpServletRequest request){
        return montar(HttpStatus.BAD_REQUEST, "Corpo da requisicao invalido ou malformado",request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> tratarTipoInvalido(MethodArgumentTypeMismatchException ex,
                                                            HttpServletRequest request){
        return montar(HttpStatus.BAD_REQUEST,"Valor invalido para o parametro "+ ex.getName(),request);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<StandardError> tratarParametroAusente(MissingServletRequestParameterException ex,
                                                                HttpServletRequest request){
        return montar(HttpStatus.BAD_REQUEST,"Parametro obrigatorio ausente" + ex.getParameterName(),request);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> tratarIntegridade(DataIntegrityViolationException ex,
                                                           HttpServletRequest request){
        return montar(HttpStatus.CONFLICT,"Operacao viola uma restricao de integridade de dados",request);
    }

    private ResponseEntity<StandardError> montar(HttpStatus status, String mensagem, HttpServletRequest request){
        StandardError corpo = new StandardError(
                LocalDateTime.now(),
                status.value(),
                mensagem,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(corpo);
    }
}
