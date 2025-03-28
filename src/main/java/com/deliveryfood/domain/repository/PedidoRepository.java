package com.deliveryfood.domain.repository;

import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
