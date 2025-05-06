package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.PedidoController;
import com.deliveryfood.api.v1.model.PedidoModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DeliveryLinks deliveryLinks;

	@Autowired
	private DeliverySecurity deliverySecurity;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

		modelMapper.map(pedido, pedidoModel);

		if (deliverySecurity.podePesquisarPedidos()) {
			pedidoModel.add(deliveryLinks.linkToPedidos("pedidos"));
		}

		if(deliverySecurity.podeGerenciarPedidos(pedido.getCodigo())) {
			if (pedido.podeSerConfirmado()) {
				pedidoModel.add(deliveryLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			}
	
			if (pedido.podeSerEntregue()) {
				pedidoModel.add(deliveryLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			}
	
			if (pedido.podeSerCancelado()) {
				pedidoModel.add(deliveryLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			}
		}

		if (deliverySecurity.podeConsultarRestaurantes()) {
			pedidoModel.getRestaurante().add(deliveryLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		}
		
		if (deliverySecurity.podeConsultarUsuariosGruposPermissoes()) {
			pedidoModel.getCliente().add(deliveryLinks.linkToUsuario(pedido.getCliente().getId()));
		}
		
		if (deliverySecurity.podeConsultarFormasPagamento()) {
			pedidoModel.getFormaPagamento().add(deliveryLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		}
		
		if (deliverySecurity.podeConsultarCidades()) {
			pedidoModel.getEnderecoEntrega().getCidade().add(deliveryLinks.linkToCidade(
					pedido.getEnderecoEntrega().getCidade().getId()));
		}
		
		// Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
		if (deliverySecurity.podeConsultarRestaurantes()) {
			pedidoModel.getItens().forEach(item -> {
				item.add(deliveryLinks.linkToProduto(
						pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produtos"));
			});
		}

		return pedidoModel;
	}
}
