package com.deliveryfood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.deliveryfood.domain.event.PedidoCanceladoEvent;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.service.EnvioEmailService;
import com.deliveryfood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener //AFTER_COMMIT é o default de phase
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();
        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
                .corpo("emails/pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }
}
