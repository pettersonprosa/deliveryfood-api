package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.CidadeController;
import com.deliveryfood.api.v1.model.CidadeModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        if (deliverySecurity.podeConsultarCidades()) {
            cidadeModel.add(deliveryLinks.linkToCidades("cidades"));
        }
        
        if (deliverySecurity.podeConsultarEstados()) {
            cidadeModel.getEstado().add(deliveryLinks.linkToEstado(cidadeModel.getEstado().getId()));
        }
        
        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);
    
        if (deliverySecurity.podeConsultarCidades()) {
            collectionModel.add(deliveryLinks.linkToCidades());
        }
        
        return collectionModel;
    }

}
