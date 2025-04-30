package com.deliveryfood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.exception.UsuarioNaoEncontradoException;
import com.deliveryfood.domain.model.Grupo;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        if (usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novoSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(passwordEncoder.encode(novoSenha));
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        
        usuario.removerGrupo(grupo);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        usuario.adicionarGrupo(grupo);
    }

    public Usuario buscarOuFalhar(Long grupoId) {
        return usuarioRepository.findById(grupoId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(grupoId));
    }

}
