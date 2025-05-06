package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.EstadoController;
import com.deliveryfood.api.v1.model.EstadoModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity; 

    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);

        modelMapper.map(estado, estadoModel);
        
        if (deliverySecurity.podeConsultarEstados()) {
            estadoModel.add(deliveryLinks.linkToEstados("estados"));
        }

        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoModel> collectionModel = super.toCollectionModel(entities);
    
        if (deliverySecurity.podeConsultarEstados()) {
            collectionModel.add(deliveryLinks.linkToEstados());
        }
        
        return collectionModel;
    }

}
