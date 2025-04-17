package com.deliveryfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.DeliveryLinks;
import com.deliveryfood.api.controller.PedidoController;
import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DeliveryLinks deliveryLinks;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

		modelMapper.map(pedido, pedidoModel);

		pedidoModel.add(deliveryLinks.linkToPedidos());

		if (pedido.podeSerConfirmado()) {
			pedidoModel.add(deliveryLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
		}

		if (pedido.podeSerEntregue()) {
			pedidoModel.add(deliveryLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
		}

		if (pedido.podeSerCancelado()) {
			pedidoModel.add(deliveryLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
		}

		pedidoModel.getRestaurante().add(deliveryLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		pedidoModel.getCliente().add(deliveryLinks.linkToUsuario(pedido.getCliente().getId()));
		pedidoModel.getFormaPagamento().add(deliveryLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		pedidoModel.getEnderecoEntrega().getCidade().add(deliveryLinks.linkToCidade(
				pedido.getEnderecoEntrega().getCidade().getId()));

		pedidoModel.getItens().forEach(item -> {
			item.add(deliveryLinks.linkToProduto(
					pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produtos"));
		});

		return pedidoModel;
	}
}
