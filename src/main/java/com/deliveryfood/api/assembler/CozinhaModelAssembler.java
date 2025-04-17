package com.deliveryfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.DeliveryLinks;
import com.deliveryfood.api.controller.CozinhaController;
import com.deliveryfood.api.model.CozinhaModel;
import com.deliveryfood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(deliveryLinks.linkToCidades("cozinhas"));
        
        return cozinhaModel;
    }

}
