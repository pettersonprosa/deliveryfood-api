package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.model.PermissaoModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler implements RepresentationModelAssembler<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;
    
    @Autowired
    private DeliverySecurity deliverySecurity;

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        CollectionModel<PermissaoModel> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (deliverySecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(deliveryLinks.linkToPermissoes());
        }
        
        return collectionModel; 
    }

}
