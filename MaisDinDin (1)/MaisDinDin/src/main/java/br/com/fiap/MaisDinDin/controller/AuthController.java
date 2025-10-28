package br.com.fiap.MaisDinDin.controller;

import br.com.fiap.MaisDinDin.model.Usuario;
import br.com.fiap.MaisDinDin.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Define a URL base para autenticação
@CrossOrigin(origins = "*") // Permite acesso do frontend
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // POST /api/auth/register - Endpoint público para criar um novo usuário
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            // A lógica de criptografar a senha e salvar está no UsuarioService
            Usuario novoUsuario = usuarioService.salvar(usuario);

            // Importante: Nunca retorne a senha na resposta da API
            novoUsuario.setSenha(null);

            // Retorna 201 Created com os dados do usuário (sem senha)
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // Se o UsuarioService lançar exceção (ex: email/CPF já existe), retorna 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // Outros erros inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar usuário.");
        }
    }

    // TODO: Adicionar endpoint de Login (@PostMapping("/login")) aqui futuramente
    // Este endpoint receberia email/senha, validaria usando Spring Security
    // e retornaria um token JWT se o login for bem-sucedido.

}