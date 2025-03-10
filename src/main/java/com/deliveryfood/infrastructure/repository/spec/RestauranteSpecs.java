package com.deliveryfood.infrastructure.repository.spec;

import com.deliveryfood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestauranteSpecs {

    public static Specification<Restaurante> comFreteGratis() {
        return (root, query, builder) -> {
            return builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
        };
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) -> {
            return builder.like(root.get("nome"), "%" + nome + "%");
        };
    }
}
