package br.com.fiap.MaisDinDin.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Import necessário para HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean // Esta anotação diz ao Spring: "Crie um objeto deste tipo e gerencie-o"
    public PasswordEncoder passwordEncoder() {
        // Retorna a implementação específica que queremos usar (BCrypt)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita a proteção CSRF (comum e necessário para APIs REST stateless)
                .csrf(csrf -> csrf.disable())

                // Configura a política de gerenciamento de sessão como STATELESS
                // (a API não guarda estado de sessão, cada requisição é independente)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define as regras de autorização para as requisições HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público (sem autenticação) para:
                        // - POST em /api/auth/register (cadastro)
                        // - POST em /api/auth/login (login - a ser criado)
                        .requestMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()

                        // Permite acesso público a documentação da API (se usar Swagger/OpenAPI)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // --- AJUSTE TEMPORÁRIO PARA TESTES ---
                        // Permite acesso público (sem autenticação) para requisições GET em:
                        // - /api/usuarios (listar todos)
                        // - /api/usuarios/{id} (buscar um específico)
                        // - /api/despesas, /api/despesas/** (adicione se precisar testar GET de despesas)
                        // - /api/receitas, /api/receitas/** (adicione se precisar testar GET de receitas)
                        // - /api/investimentos, /api/investimentos/** (adicione se precisar testar GET de investimentos)
                        .requestMatchers(HttpMethod.GET,
                                "/api/usuarios", "/api/usuarios/**",
                                "/api/despesas", "/api/despesas/**", // Descomente para testar GET de despesas
                                "/api/receitas", "/api/receitas/**", // Descomente para testar GET de receitas
                                "/api/investimentos", "/api/investimentos/**" // Descomente para testar GET de investimentos
                        ).permitAll()
                        // --- FIM DO AJUSTE TEMPORÁRIO ---

                        // Qualquer outra requisição (ex: PUT, DELETE, POST em /api/despesas, etc.)
                        // exige que o usuário esteja autenticado.
                        .anyRequest().authenticated()
                );
        // Futuramente, aqui será adicionado o filtro para validar tokens JWT

        return http.build();
    }
}