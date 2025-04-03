package com.deliveryfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.model.FotoProduto;
import com.deliveryfood.domain.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        return produtoRepository.save(foto);
    }
}
