package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.RestauranteProdutoController;
import com.deliveryfood.api.v1.model.ProdutoModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliveryFood;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoModel);

        if (deliveryFood.podeConsultarRestaurantes()) {
            produtoModel.add(deliveryLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
            produtoModel.add(deliveryLinks.linkToFotoProduto(
                    produto.getRestaurante().getId(), produto.getId(), "foto"));
        }

        return produtoModel;
    }

}
