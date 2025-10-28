package br.com.fiap.MaisDinDin.repository;

import br.com.fiap.MaisDinDin.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByUsuarioId(Long usuarioId);
}