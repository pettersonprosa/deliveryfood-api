package com.deliveryfood.infrastructure.repository.spec;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.repository.filter.PedidoFilter;

import jakarta.persistence.criteria.Predicate;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, builder) -> {
            if (Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            var predicates = new ArrayList<Predicate>();

            if (filtro.getClienteId() != null) {
                predicates.add(builder.equal(root.get("cliente").get("id"), filtro.getClienteId()));
            }

            if (filtro.getRestauranteId() != null) {
                predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
            }

            if (filtro.getDataCriacaoInicio() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                        filtro.getDataCriacaoInicio()));
            }

            if (filtro.getDataCriacaoFim() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
                        filtro.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
