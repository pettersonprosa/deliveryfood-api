package com.deliveryfood.domain.service;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Estado;
import com.deliveryfood.domain.repository.CidadeRepository;
import com.deliveryfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de estado com código %d", estadoId)));

        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId){

        if (!cidadeRepository.existsById(cidadeId)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com código %d", cidadeId));
        }

        try{
            cidadeRepository.deleteById(cidadeId);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removida, pois está em uso.", cidadeId));
        }
    }
}
