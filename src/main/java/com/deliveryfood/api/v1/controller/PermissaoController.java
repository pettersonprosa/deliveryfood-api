package com.deliveryfood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.v1.assembler.PermissaoModelAssembler;
import com.deliveryfood.api.v1.model.PermissaoModel;
import com.deliveryfood.domain.model.Permissao;
import com.deliveryfood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/v1/permissoes")
public class PermissaoController {

    @Autowired
    private PermissaoRepository permissaoRepository;
    
    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;
    
    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        
        return permissaoModelAssembler.toCollectionModel(todasPermissoes);
    }   
}
