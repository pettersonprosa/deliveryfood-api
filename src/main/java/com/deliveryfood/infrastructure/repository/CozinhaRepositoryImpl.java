package com.deliveryfood.infrastructure.repository;

import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.repository.CozinhaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cozinha> listar() {
        TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);

        return query.getResultList();
        // a string informada é um JPQL, que é uma linguagem de consulta do JPA, que parece com SQL, mas não é. A consulta aqui é feita em objetos e não tabelas.
    }

    @Override
    public List<Cozinha> consultaPorNome(String nome) {
        return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    @Override
    public Cozinha buscar(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Override
    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Cozinha cozinha = buscar(id);

        if (cozinha == null) {
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(cozinha);
    }
}
