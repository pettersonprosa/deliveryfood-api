package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.PedidoController;
import com.deliveryfood.api.v1.model.PedidoResumoModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

        modelMapper.map(pedido, pedidoModel);

        if (deliverySecurity.podePesquisarPedidos()) {
            pedidoModel.add(deliveryLinks.linkToPedidos("pedidos"));
        }
        
        if (deliverySecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(deliveryLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }
    
        if (deliverySecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(deliveryLinks.linkToUsuario(pedido.getCliente().getId()));
        }


        return pedidoModel;
    }
}
