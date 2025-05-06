package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.RestauranteController;
import com.deliveryfood.api.v1.model.RestauranteBasicoModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity;

    public RestauranteBasicoModelAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }

    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        RestauranteBasicoModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);

        if (deliverySecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(deliveryLinks.linkToRestaurantes("restaurantes"));
        }
        
        if (deliverySecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(deliveryLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteBasicoModel> collectionModel = super.toCollectionModel(entities);
    
        if (deliverySecurity.podeConsultarRestaurantes()) {
            collectionModel.add(deliveryLinks.linkToRestaurantes());
        }
                
        return collectionModel;
    }

}
