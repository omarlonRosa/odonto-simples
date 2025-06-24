package com.odontosimples.service;

import com.odontosimples.entity.Usuario;
import com.odontosimples.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        return usuario;
    }

    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
        
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);
        
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setTelefone(usuarioAtualizado.getTelefone());
        
        // Só atualiza email se for diferente e não existir
        if (!usuario.getEmail().equals(usuarioAtualizado.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
                throw new RuntimeException("Email já está em uso");
            }
            usuario.setEmail(usuarioAtualizado.getEmail());
        }
        
        return usuarioRepository.save(usuario);
    }

    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarPorId(id);
        
        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }
        
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmailAndActiveTrue(email);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findByActiveTrue();
    }

    public List<Usuario> listarPorRole(Usuario.Role role) {
        return usuarioRepository.findByRoleAndActiveTrue(role);
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingAndActiveTrue(nome);
    }

    public void ativar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.activate();
        usuarioRepository.save(usuario);
    }

    public void desativar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.deactivate();
        usuarioRepository.save(usuario);
    }

    public long contarPorRole(Usuario.Role role) {
        return usuarioRepository.countByRoleAndActiveTrue(role);
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario alterarRole(Long id, Usuario.Role novaRole) {
        Usuario usuario = buscarPorId(id);
        usuario.setRole(novaRole);
        return usuarioRepository.save(usuario);
    }
}


