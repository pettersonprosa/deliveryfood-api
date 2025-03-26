package com.deliveryfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deliveryfood.api.exceptionhandler.ApiExceptionHandler;
import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.exception.UsuarioNaoEncontradoException;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novoSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if(usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novoSenha);
    }

    public Usuario buscarOuFalhar(Long grupoId) {
        return usuarioRepository.findById(grupoId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(grupoId));
    }

}
