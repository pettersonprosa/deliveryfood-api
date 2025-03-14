package com.deliveryfood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {
    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
