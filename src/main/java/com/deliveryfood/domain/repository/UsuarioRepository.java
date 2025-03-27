package com.deliveryfood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

    Optional<Usuario> findByEmail(String email);

}
