package com.deliveryfood.domain.repository;

import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

}
