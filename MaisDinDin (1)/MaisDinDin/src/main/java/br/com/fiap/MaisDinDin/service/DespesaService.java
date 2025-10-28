package br.com.fiap.MaisDinDin.service;

import br.com.fiap.MaisDinDin.model.Despesa;
import br.com.fiap.MaisDinDin.repository.DespesaRepository;
// Importar UsuarioRepository se precisar validar o usuário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {
    @Autowired private DespesaRepository repo;
    // @Autowired private UsuarioRepository userRepo; // Para validar usuário

    @Transactional(readOnly = true) public List<Despesa> listarTodas() { return repo.findAll(); }
    @Transactional(readOnly = true) public Optional<Despesa> buscarPorId(Long id) { return repo.findById(id); }
    @Transactional(readOnly = true) public List<Despesa> buscarPorUsuarioId(Long usuarioId) { return repo.findByUsuarioId(usuarioId); }
    @Transactional public Despesa salvar(Despesa despesa) {
        // Validar se o usuario associado existe (despesa.getUsuario().getId())
        // if(!userRepo.existsById(despesa.getUsuario().getId())) throw new RuntimeException("Usuário não encontrado");
        return repo.save(despesa);
    }
    @Transactional public Optional<Despesa> atualizar(Long id, Despesa upd) {
        return repo.findById(id).map(d -> {
            d.setDescricao(upd.getDescricao()); d.setValor(upd.getValor());
            d.setData(upd.getData()); d.setCategoria(upd.getCategoria());
            return repo.save(d);
        });
    }
    @Transactional public boolean deletar(Long id) {
        if(repo.existsById(id)){ repo.deleteById(id); return true; } return false;
    }
}