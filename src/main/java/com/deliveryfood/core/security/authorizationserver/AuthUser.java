package com.deliveryfood.core.security.authorizationserver;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.deliveryfood.domain.model.Usuario;

import lombok.Getter;

@Getter
public class AuthUser extends User {

    private Long userId;
	private String fullName;
	
	public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		
		this.userId = usuario.getId();
		this.fullName = usuario.getNome();
	}

}
