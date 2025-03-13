package com.deliveryfood.domain.service;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Estado;
import com.deliveryfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_NAO_ENCONTRATO = "Não existe um cadastro de estado em código %d";
    public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void excluir(Long estadoId) {

        if (!estadoRepository.existsById(estadoId)) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_ESTADO_NAO_ENCONTRATO, estadoId));
        }

        try {
            estadoRepository.deleteById(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_ESTADO_NAO_ENCONTRATO, estadoId)));
    }
}
