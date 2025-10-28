package br.com.fiap.MaisDinDin.service;

import br.com.fiap.MaisDinDin.model.Receita; // Importa o modelo Receita
import br.com.fiap.MaisDinDin.repository.ReceitaRepository; // Importa o repositório Receita
// Importar UsuarioRepository se precisar validar o usuário
// import br.com.fiap.MaisDinDin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository; // Injeta o repositório específico de Receita

    // @Autowired
    // private UsuarioRepository usuarioRepository; // Descomente se precisar validar o usuário

    // Método GET (Consultar Todas)
    @Transactional(readOnly = true)
    public List<Receita> listarTodas() {
        return receitaRepository.findAll();
    }

    // Método GET (Consultar por ID)
    @Transactional(readOnly = true)
    public Optional<Receita> buscarPorId(Long id) {
        return receitaRepository.findById(id);
    }


    @Transactional(readOnly = true)
    public List<Receita> buscarPorUsuarioId(Long usuarioId) {
        return receitaRepository.findByUsuarioId(usuarioId);
    }

    // Método POST (Criar)
    @Transactional
    public Receita salvar(Receita receita) {
        // Validação opcional: Verificar se o usuário associado existe
        // if (!usuarioRepository.existsById(receita.getUsuario().getId())) {
        //     throw new RuntimeException("Usuário associado à receita não encontrado!");
        // }
        // Adicione outras validações se necessário (ex: valor positivo)
        return receitaRepository.save(receita);
    }

    // Método PUT (Atualizar)
    @Transactional
    public Optional<Receita> atualizar(Long id, Receita receitaAtualizada) {
        return receitaRepository.findById(id)
                .map(receitaExistente -> {
                    // Atualiza os campos da receita existente com os novos dados
                    receitaExistente.setDescricao(receitaAtualizada.getDescricao());
                    receitaExistente.setValor(receitaAtualizada.getValor());
                    receitaExistente.setData(receitaAtualizada.getData());
                    receitaExistente.setCategoria(receitaAtualizada.getCategoria());
                    // Geralmente não se altera o usuário associado aqui
                    return receitaRepository.save(receitaExistente);
                }); // Retorna Optional.empty() se não encontrar a receita com o ID
    }

    // Método DELETE (Deletar)
    @Transactional
    public boolean deletar(Long id) {
        if (receitaRepository.existsById(id)) {
            receitaRepository.deleteById(id);
            return true; // Indica que foi deletado com sucesso
        }
        return false; // Indica que não foi encontrado para deletar
    }
}