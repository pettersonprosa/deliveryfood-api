package com.deliveryfood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.StatusPedido;

import jakarta.transaction.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            pedidoId, pedido.getStatus().getDescricao(), StatusPedido.CONFIRMADO.getDescricao()));
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            pedidoId, pedido.getStatus().getDescricao(), StatusPedido.CANCELADO.getDescricao()));
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            pedidoId, pedido.getStatus().getDescricao(), StatusPedido.ENTREGUE.getDescricao()));
        }

        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

}
