package com.deliveryfood.domain.service;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Estado;
import com.deliveryfood.domain.repository.CidadeRepository;
import com.deliveryfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    public static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com código %d";
    public static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();

        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId){

        if (!cidadeRepository.existsById(cidadeId)) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
        }

        try{
            cidadeRepository.deleteById(cidadeId);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
    }
}
