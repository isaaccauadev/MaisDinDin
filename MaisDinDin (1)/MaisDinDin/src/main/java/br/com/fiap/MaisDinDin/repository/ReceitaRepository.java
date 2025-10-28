package br.com.fiap.MaisDinDin.repository;

import br.com.fiap.MaisDinDin.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByUsuarioId(Long usuarioId);
}