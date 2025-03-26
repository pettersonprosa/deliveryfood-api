package com.deliveryfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findTodasByNomeContaining(String nome); // coloca o "like %nome%

    Optional<Cozinha> findByNome(String nome);
}
