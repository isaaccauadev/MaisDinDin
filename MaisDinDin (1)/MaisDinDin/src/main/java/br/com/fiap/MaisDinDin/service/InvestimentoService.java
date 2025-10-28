package br.com.fiap.MaisDinDin.service; // Confirme se o pacote base está correto

import br.com.fiap.MaisDinDin.model.Investimento; // Importa o modelo Investimento
import br.com.fiap.MaisDinDin.repository.InvestimentoRepository; // Importa o repositório Investimento
// Importar UsuarioRepository se precisar validar o usuário
// import br.com.fiap.MaisDinDin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InvestimentoService {

    @Autowired
    private InvestimentoRepository investimentoRepository; // Injeta o repositório específico de Investimento

    // @Autowired
    // private UsuarioRepository usuarioRepository; // Descomente se precisar validar o usuário

    // Método GET (Consultar Todos)
    @Transactional(readOnly = true)
    public List<Investimento> listarTodos() {
        return investimentoRepository.findAll();
    }

    // Método GET (Consultar por ID)
    @Transactional(readOnly = true)
    public Optional<Investimento> buscarPorId(Long id) {
        return investimentoRepository.findById(id);
    }

    // Método GET (Consultar por ID do Usuário)
    @Transactional(readOnly = true)
    public List<Investimento> buscarPorUsuarioId(Long usuarioId) {
        // Assume que o método findByUsuarioId existe em InvestimentoRepository
        return investimentoRepository.findByUsuarioId(usuarioId);
    }

    // Método POST (Criar)
    @Transactional
    public Investimento salvar(Investimento investimento) {
        // Validação opcional: Verificar se o usuário associado existe
        // if (!usuarioRepository.existsById(investimento.getUsuario().getId())) {
        //     throw new RuntimeException("Usuário associado ao investimento não encontrado!");
        // }
        // Adicione outras validações se necessário
        return investimentoRepository.save(investimento);
    }

    // Método PUT (Atualizar)
    @Transactional
    public Optional<Investimento> atualizar(Long id, Investimento investimentoAtualizado) {
        return investimentoRepository.findById(id)
                .map(investimentoExistente -> {
                    // Atualiza os campos do investimento existente
                    investimentoExistente.setNomeInvestimento(investimentoAtualizado.getNomeInvestimento());
                    investimentoExistente.setValorAplicado(investimentoAtualizado.getValorAplicado());
                    investimentoExistente.setDataAplicacao(investimentoAtualizado.getDataAplicacao());
                    investimentoExistente.setTipo(investimentoAtualizado.getTipo());
                    // Geralmente não se altera o usuário associado aqui
                    return investimentoRepository.save(investimentoExistente);
                }); // Retorna Optional.empty() se não encontrar o investimento com o ID
    }

    // Método DELETE (Deletar)
    @Transactional
    public boolean deletar(Long id) {
        if (investimentoRepository.existsById(id)) {
            investimentoRepository.deleteById(id);
            return true; // Indica que foi deletado com sucesso
        }
        return false; // Indica que não foi encontrado para deletar
    }
}