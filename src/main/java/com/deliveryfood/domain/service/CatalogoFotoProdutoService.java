package com.deliveryfood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.FotoProdutoNaoEncontradaException;
import com.deliveryfood.domain.model.FotoProduto;
import com.deliveryfood.domain.repository.ProdutoRepository;
import com.deliveryfood.domain.service.FotoStorageService.NovaFoto;

import jakarta.transaction.Transactional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
        
        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();
        
        fotoStorage.remover(fotoProduto.getNomeArquivo());
    }

    @Transactional
    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }
    
}
