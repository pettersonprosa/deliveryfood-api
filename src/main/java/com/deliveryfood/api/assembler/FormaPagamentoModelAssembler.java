package com.deliveryfood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.model.FormaPagamentoModel;
import com.deliveryfood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> formasPagamento) { // TODO Rever troca de List<FormaPagamento> para Collection<FormaPagamento> por causar de Set<FormaPagamento> no Restaurante
        return formasPagamento.stream()
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }
}
