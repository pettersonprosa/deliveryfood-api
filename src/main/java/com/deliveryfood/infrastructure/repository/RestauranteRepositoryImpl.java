package com.deliveryfood.infrastructure.repository;

import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        var jpql = "FROM Restaurante WHERE nome LIKE :nome and taxaFrete BETWEEN :taxaInicial and :taxaFinal";
        // Se colocar o % dentro do JPQL irá ocorrer um erro, diferente de colocar no @Query que é tratado pelo SDJ

        return manager.createQuery(jpql, Restaurante.class)
                .setParameter("nome", "%" + nome + "%")
                .setParameter("taxaInicial", taxaFreteInicial)
                .setParameter("taxaFinal", taxaFreteFinal)
                .getResultList();
    }
}
