package com.deliveryfood.domain.listener;

import com.deliveryfood.domain.service.EnvioEmailService;
import com.deliveryfood.domain.service.EnvioEmailService.Mensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.deliveryfood.domain.event.PedidoConfirmadoEvent;
import com.deliveryfood.domain.model.Pedido;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) //AFTER_COMMIT é o default
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();
        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }
}
