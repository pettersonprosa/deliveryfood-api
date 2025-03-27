package com.deliveryfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.PermissaoNaoEncontradaException;
import com.deliveryfood.domain.model.Permissao;
import com.deliveryfood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }

}
