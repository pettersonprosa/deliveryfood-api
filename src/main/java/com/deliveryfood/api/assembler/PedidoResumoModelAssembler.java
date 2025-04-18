package com.deliveryfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.DeliveryLinks;
import com.deliveryfood.api.controller.PedidoController;
import com.deliveryfood.api.model.PedidoResumoModel;
import com.deliveryfood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(deliveryLinks.linkToPedidos("pedidos"));
        pedidoModel.getRestaurante().add(deliveryLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        pedidoModel.getCliente().add(deliveryLinks.linkToUsuario(pedido.getCliente().getId()));

        return pedidoModel;
    }
}
