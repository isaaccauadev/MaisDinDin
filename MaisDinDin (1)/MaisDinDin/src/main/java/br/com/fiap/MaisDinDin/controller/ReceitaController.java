package br.com.fiap.MaisDinDin.controller;

import br.com.fiap.MaisDinDin.model.Receita; // Importa o modelo Receita
import br.com.fiap.MaisDinDin.service.ReceitaService; // Importa o serviço Receita
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/receitas") // URL base para receitas
@CrossOrigin(origins = "*") // Permite acesso do frontend (ajuste em produção)
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService; // Injeta o serviço correspondente

    // GET /api/receitas - Listar todas
    @GetMapping
    public ResponseEntity<List<Receita>> listarTodasReceitas() {
        // TODO: Em uma aplicação real, filtrar por usuário logado (Spring Security)
        List<Receita> receitas = receitaService.listarTodas();
        return ResponseEntity.ok(receitas); // Retorna 200 OK
    }

    // GET /api/receitas/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Receita> buscarReceitaPorId(@PathVariable Long id) {
        // TODO: Verificar se a receita pertence ao usuário logado
        Optional<Receita> receita = receitaService.buscarPorId(id);
        return receita.map(ResponseEntity::ok) // 200 OK se encontrar
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 Not Found se não encontrar
    }

    // GET /api/receitas/usuario/{usuarioId} - Buscar receitas por ID do usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Receita>> buscarReceitasPorUsuario(@PathVariable Long usuarioId) {
        // TODO: Verificar se o usuarioId corresponde ao usuário logado
        List<Receita> receitas = receitaService.buscarPorUsuarioId(usuarioId); // Assume que este método existe no Service
        return ResponseEntity.ok(receitas);
    }

    // POST /api/receitas - Criar nova receita
    @PostMapping
    public ResponseEntity<Receita> criarReceita(@Valid @RequestBody Receita receita) {
        // TODO: Associar a receita ao usuário logado (via Spring Security)
        // receita.setUsuario(usuarioLogado);
        Receita novaReceita = receitaService.salvar(receita);
        return new ResponseEntity<>(novaReceita, HttpStatus.CREATED); // Retorna 201 Created
    }

    // PUT /api/receitas/{id} - Atualizar receita existente
    @PutMapping("/{id}")
    public ResponseEntity<Receita> atualizarReceita(@PathVariable Long id, @Valid @RequestBody Receita receitaAtualizada) {
        // TODO: Verificar se a receita pertence ao usuário logado antes de atualizar
        return receitaService.atualizar(id, receitaAtualizada)
                .map(ResponseEntity::ok) // 200 OK se atualizou
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found se não encontrou
    }

    // DELETE /api/receitas/{id} - Deletar receita
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
        // TODO: Verificar se a receita pertence ao usuário logado antes de deletar
        if (receitaService.deletar(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content (sucesso sem corpo)
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found se não existia
        }
    }
}