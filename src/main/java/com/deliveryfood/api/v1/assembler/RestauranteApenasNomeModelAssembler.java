package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.RestauranteController;
import com.deliveryfood.api.v1.model.RestauranteApenasNomeModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Restaurante;


@Component
public class RestauranteApenasNomeModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity;

    public RestauranteApenasNomeModelAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        RestauranteApenasNomeModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        if (deliverySecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(deliveryLinks.linkToRestaurantes("restaurantes"));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteApenasNomeModel> collectionModel = super.toCollectionModel(entities);
    
        if (deliverySecurity.podeConsultarRestaurantes()) {
            collectionModel.add(deliveryLinks.linkToRestaurantes());
        }
                
        return collectionModel;
    }
}
