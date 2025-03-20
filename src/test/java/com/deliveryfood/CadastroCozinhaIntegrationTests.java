package com.deliveryfood;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.deliveryfood.domain.exception.CozinhaNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.service.CadastroCozinhaService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
    public void testarCadastroCozinhaComSucesso() {
        // cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // ação
        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        // validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    public void testarCadastroCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            cadastroCozinha.salvar(novaCozinha);
        });

    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {

        Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
            cadastroCozinha.excluir(1L);
            ;
        });
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
        Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
                cadastroCozinha.excluir(999L);
            });
    }

}
