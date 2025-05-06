package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.GrupoController;
import com.deliveryfood.api.v1.model.GrupoModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity; 

    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }

    @Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);

        modelMapper.map(grupo, grupoModel);

        if (deliverySecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoModel.add(deliveryLinks.linkToGrupos("grupos"));
            grupoModel.add(deliveryLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }

        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        CollectionModel<GrupoModel> collectionModel = super.toCollectionModel(entities);
    
        if (deliverySecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(deliveryLinks.linkToGrupos());
        }
        
        return collectionModel;
    }

}
