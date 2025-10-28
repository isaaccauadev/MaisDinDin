package br.com.fiap.MaisDinDin.controller;

import br.com.fiap.MaisDinDin.model.Despesa;
import br.com.fiap.MaisDinDin.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/despesas")
@CrossOrigin(origins = "*")
public class DespesaController {
    @Autowired private DespesaService service;

    @GetMapping
    public ResponseEntity<List<Despesa>> getAllDespesas() {
        // TODO: Filtrar por usuário logado (via Spring Security)
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> getDespesaById(@PathVariable Long id) {
        // TODO: Verificar se a despesa pertence ao usuário logado
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}") // Exemplo de rota para buscar por usuário
    public ResponseEntity<List<Despesa>> getDespesasByUsuario(@PathVariable Long usuarioId) {
        // TODO: Verificar se o usuarioId é o mesmo do usuário logado
        return ResponseEntity.ok(service.buscarPorUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Despesa> createDespesa(@Valid @RequestBody Despesa despesa) {
        // TODO: Pegar o usuário logado e associar à despesa
        // despesa.setUsuario(usuarioLogado);
        Despesa newDespesa = service.salvar(despesa);
        return new ResponseEntity<>(newDespesa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesa> updateDespesa(@PathVariable Long id, @Valid @RequestBody Despesa despesa) {
        // TODO: Verificar se a despesa pertence ao usuário logado
        return service.atualizar(id, despesa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDespesa(@PathVariable Long id) {
        // TODO: Verificar se a despesa pertence ao usuário logado
        return service.deletar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}