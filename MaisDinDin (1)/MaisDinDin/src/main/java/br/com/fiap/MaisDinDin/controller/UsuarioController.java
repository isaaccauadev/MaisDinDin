package br.com.fiap.MaisDinDin.controller;

import br.com.fiap.MaisDinDin.model.Usuario;
import br.com.fiap.MaisDinDin.service.UsuarioService;
import jakarta.validation.Valid; // Para validar o @RequestBody
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

   /* @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mover endpoint de registro para um AuthController pode ser mais semântico
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario newUser = service.salvar(usuario);
            newUser.setSenha(null); // Nunca retorne a senha
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return service.atualizar(id, usuario)
                .map(u -> { u.setSenha(null); return ResponseEntity.ok(u); })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return service.deletar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }*/
}