package br.com.fiap.MaisDinDin.service;

import br.com.fiap.MaisDinDin.model.Usuario;
import br.com.fiap.MaisDinDin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository repo;
    @Autowired private PasswordEncoder encoder;

    @Transactional(readOnly = true) public List<Usuario> listarTodos() { return repo.findAll(); }
    @Transactional(readOnly = true) public Optional<Usuario> buscarPorId(Long id) { return repo.findById(id); }
    @Transactional public Usuario salvar(Usuario usuario) {
        if (repo.existsByEmail(usuario.getEmail())) throw new RuntimeException("Email já existe");
        if (repo.existsByCpf(usuario.getCpf())) throw new RuntimeException("CPF já existe");
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return repo.save(usuario);
    }
    @Transactional public Optional<Usuario> atualizar(Long id, Usuario upd) {
        return repo.findById(id).map(u -> {
            u.setNome(upd.getNome()); u.setEmail(upd.getEmail());
            if (upd.getSenha() != null && !upd.getSenha().isEmpty()) u.setSenha(encoder.encode(upd.getSenha()));
            return repo.save(u);
        });
    }
    @Transactional public boolean deletar(Long id) {
        if(repo.existsById(id)){ repo.deleteById(id); return true; } return false;
    }
}