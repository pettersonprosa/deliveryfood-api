package com.deliveryfood.api.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModel extends RepresentationModel<CidadeModel> {

    private Long id;
    private String nome;
    private EstadoModel estado;
    
}
