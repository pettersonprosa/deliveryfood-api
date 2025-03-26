package com.deliveryfood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        this(String.format("Não existe um cadastro de grupo com código %d", grupoId));
    }

}
