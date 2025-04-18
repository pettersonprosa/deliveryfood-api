package com.deliveryfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.DeliveryLinks;
import com.deliveryfood.api.controller.RestauranteController;
import com.deliveryfood.api.model.RestauranteModel;
import com.deliveryfood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(deliveryLinks.linkToRestaurantes("restaurantes"));
        restauranteModel.getCozinha().add(deliveryLinks.linkToCozinha(restaurante.getCozinha().getId()));

        // Null-safety para endereço/cidade
        if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
            restauranteModel.getEndereco().getCidade().add(
                    deliveryLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
        }

        restauranteModel.add(deliveryLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
        restauranteModel.add(deliveryLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(deliveryLinks.linkToRestaurantes());
    }
}
