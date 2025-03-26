package com.deliveryfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.GrupoNaoEncontradoException;
import com.deliveryfood.domain.model.Grupo;
import com.deliveryfood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

    public static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso.";

    @Autowired
    private GrupoRepository grupoRepository;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long grupoId) {

        if (!grupoRepository.existsById(grupoId)) {
            throw new GrupoNaoEncontradoException(grupoId);
        }

        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
        }
    }

    public Grupo buscarOuFalhar(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }
}
