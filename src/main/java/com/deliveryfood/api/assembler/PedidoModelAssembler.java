package com.deliveryfood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.controller.CidadeController;
import com.deliveryfood.api.controller.FormaPagamentoController;
import com.deliveryfood.api.controller.PedidoController;
import com.deliveryfood.api.controller.RestauranteController;
import com.deliveryfood.api.controller.RestauranteProdutoController;
import com.deliveryfood.api.controller.UsuarioController;
import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

		modelMapper.map(pedido, pedidoModel);

		TemplateVariables pageVariables = new TemplateVariables(
				new TemplateVariable("page", VariableType.REQUEST_PARAM),
				new TemplateVariable("size", VariableType.REQUEST_PARAM),
				new TemplateVariable("sort", VariableType.REQUEST_PARAM));

		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));

		String pedidoUrl = linkTo(PedidoController.class).toUri().toString();

		pedidoModel.add(Link.of(UriTemplate.of(pedidoUrl, pageVariables.concat(filtroVariables)), "pedidos"));

		// pedidoModel.add(linkTo(PedidoController.class)
		// .withRel("pedidos"));

		pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
				.buscar(pedido.getRestaurante().getId()))
				.withSelfRel());

		pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
				.buscar(pedido.getCliente().getId()))
				.withSelfRel());

		// Foi passado null no segundo argumento do método buscar, porque é
		// indiferente para construção da URL do recurso de forma de pagamento
		pedidoModel.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
				.buscar(pedido.getFormaPagamento().getId(), null))
				.withSelfRel());

		pedidoModel.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
				.buscar(pedido.getEnderecoEntrega().getCidade().getId()))
				.withSelfRel());

		pedidoModel.getItens().forEach(item -> {
			item.add(linkTo(methodOn(RestauranteProdutoController.class)
					.buscar(pedidoModel.getRestaurante().getId(), item.getProdutoId()))
					.withSelfRel());
		});

		return pedidoModel;
	}
}
