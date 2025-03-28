package com.deliveryfood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(Long pedidoId) {
        this(String.format("Não existe um pedido com código %d", pedidoId));
    }

}
