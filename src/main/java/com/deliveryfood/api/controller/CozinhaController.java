package com.deliveryfood.api.controller;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.repository.CozinhaRepository;
import com.deliveryfood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if (cozinha != null) {
            return ResponseEntity.ok(cozinha);
        }

//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinha.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);

        if (cozinhaAtual != null) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
            cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
            return ResponseEntity.ok(cozinhaAtual);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
        try {
            cadastroCozinha.excluir(cozinhaId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
