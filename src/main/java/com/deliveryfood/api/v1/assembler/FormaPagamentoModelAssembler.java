package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.FormaPagamentoController;
import com.deliveryfood.api.v1.model.FormaPagamentoModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler
        extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired 
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity;

    public FormaPagamentoModelAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoModel.class);
    }

    @Override
    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        FormaPagamentoModel formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);
        
        modelMapper.map(formaPagamento, formaPagamentoModel);

        if (deliverySecurity.podeConsultarFormasPagamento()) {
            formaPagamentoModel.add(deliveryLinks.linkToFormasPagamento("formasPagamentos"));
        }

        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        CollectionModel<FormaPagamentoModel> collectionModel = super.toCollectionModel(entities);
    
        if (deliverySecurity.podeConsultarFormasPagamento()) {
            collectionModel.add(deliveryLinks.linkToFormasPagamento());
        }

        return collectionModel;
    }

}
