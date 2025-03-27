package com.deliveryfood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        // Verifica se já existe um usuário com o mesmo e-mail no banco de dados 
        // e se esse usuário encontrado é diferente do que estamos tentando salvar.
        // Isso impede a duplicação de e-mails, mas permite atualizar os dados do próprio usuário.
        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(
                String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novoSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novoSenha);
    }

    public Usuario buscarOuFalhar(Long grupoId) {
        return usuarioRepository.findById(grupoId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(grupoId));
    }

}
