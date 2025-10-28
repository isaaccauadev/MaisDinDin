package br.com.fiap.MaisDinDin.controller;

import br.com.fiap.MaisDinDin.model.Investimento; // Importa o modelo Investimento
import br.com.fiap.MaisDinDin.service.InvestimentoService; // Importa o serviço Investimento
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/investimentos") // URL base para investimentos
@CrossOrigin(origins = "*") // Permite acesso do frontend (ajuste em produção)
public class InvestimentoController {

    @Autowired
    private InvestimentoService investimentoService; // Injeta o serviço correspondente

    // GET /api/investimentos - Listar todos
    @GetMapping
    public ResponseEntity<List<Investimento>> listarTodosInvestimentos() {
        // TODO: Em uma aplicação real, filtrar por usuário logado (Spring Security)
        List<Investimento> investimentos = investimentoService.listarTodos();
        return ResponseEntity.ok(investimentos); // Retorna 200 OK
    }

    // GET /api/investimentos/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Investimento> buscarInvestimentoPorId(@PathVariable Long id) {
        // TODO: Verificar se o investimento pertence ao usuário logado
        Optional<Investimento> investimento = investimentoService.buscarPorId(id);
        return investimento.map(ResponseEntity::ok) // 200 OK se encontrar
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 Not Found se não encontrar
    }

    // GET /api/investimentos/usuario/{usuarioId} - Buscar investimentos por ID do usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Investimento>> buscarInvestimentosPorUsuario(@PathVariable Long usuarioId) {
        // TODO: Verificar se o usuarioId corresponde ao usuário logado
        List<Investimento> investimentos = investimentoService.buscarPorUsuarioId(usuarioId); // Assume que este método existe no Service
        return ResponseEntity.ok(investimentos);
    }

    // POST /api/investimentos - Criar novo investimento
    @PostMapping
    public ResponseEntity<Investimento> criarInvestimento(@Valid @RequestBody Investimento investimento) {
        // TODO: Associar o investimento ao usuário logado (via Spring Security)
        // investimento.setUsuario(usuarioLogado);
        Investimento novoInvestimento = investimentoService.salvar(investimento);
        return new ResponseEntity<>(novoInvestimento, HttpStatus.CREATED); // Retorna 201 Created
    }

    // PUT /api/investimentos/{id} - Atualizar investimento existente
    @PutMapping("/{id}")
    public ResponseEntity<Investimento> atualizarInvestimento(@PathVariable Long id, @Valid @RequestBody Investimento investimentoAtualizado) {
        // TODO: Verificar se o investimento pertence ao usuário logado antes de atualizar
        return investimentoService.atualizar(id, investimentoAtualizado)
                .map(ResponseEntity::ok) // 200 OK se atualizou
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found se não encontrou
    }

    // DELETE /api/investimentos/{id} - Deletar investimento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInvestimento(@PathVariable Long id) {
        // TODO: Verificar se o investimento pertence ao usuário logado antes de deletar
        if (investimentoService.deletar(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content (sucesso sem corpo)
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found se não existia
        }
    }
}