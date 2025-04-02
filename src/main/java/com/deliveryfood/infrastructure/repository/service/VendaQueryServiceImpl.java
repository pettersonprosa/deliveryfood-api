package com.deliveryfood.infrastructure.repository.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.filter.VendaDiaria;
import com.deliveryfood.domain.filter.VendaDiariaFilter;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.service.VendaQueryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendaDiarias(VendaDiariaFilter filtro) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var functionDateDataCriacao = builder.function(
                "date", LocalDate.class, root.get("dataCriacao"));

        var selection = builder.construct(VendaDiaria.class, // contrua venda diaria a partir da seleção
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();

    }

}
