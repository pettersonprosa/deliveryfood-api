package com.deliveryfood.domain.service;

import com.deliveryfood.domain.exception.CozinhaNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {

    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long cozinhaId) {

        if (!cozinhaRepository.existsById(cozinhaId)) {
            throw new CozinhaNaoEncontradaException(cozinhaId);
        }

        try {
            cozinhaRepository.deleteById(cozinhaId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Cozinha buscarOuFalha(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
    }
}
