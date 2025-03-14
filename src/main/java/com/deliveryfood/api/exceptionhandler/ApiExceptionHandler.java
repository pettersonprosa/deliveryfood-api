package com.deliveryfood.api.exceptionhandler;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEstadoNaoEncontradoException(EntidadeNaoEncontradaException e) {
        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage()).build();


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarEstadoNaoEncontradoException(NegocioException e) {
        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException() {
        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem("O tipo de mídia não é aceito.").build();

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problema);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeNaoEncontradaException e){
        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
    }

}
