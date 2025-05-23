package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.RestauranteProdutoFotoController;
import com.deliveryfood.api.v1.model.FotoProdutoModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {

        FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);

        // Quem pode consultar restaurantes, também pode consultar os produtos e fotos
        if (deliverySecurity.podeConsultarRestaurantes()) {
            fotoProdutoModel.add(deliveryLinks.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId()));
            fotoProdutoModel.add(deliveryLinks.linkToProduto(
                    foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        }
        return fotoProdutoModel;
    }

}
