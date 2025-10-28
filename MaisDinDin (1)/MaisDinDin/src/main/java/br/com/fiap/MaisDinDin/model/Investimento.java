package br.com.fiap.MaisDinDin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "T_INVESTIMENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investimento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INVESTIMENTO")
    @SequenceGenerator(name = "SEQ_INVESTIMENTO", sequenceName = "SEQ_INVESTIMENTO", allocationSize = 1)
    @Column(name = "id_investimento")
    private Long id;

    @NotBlank(message = "Nome do investimento é obrigatório")
    @Column(name = "nm_investimento", nullable = false, length = 100)
    private String nomeInvestimento;

    @NotNull(message = "Valor aplicado é obrigatório")
    @Positive(message = "Valor aplicado deve ser positivo")
    @Column(name = "vl_aplicado", nullable = false)
    private Double valorAplicado;

    @NotNull(message = "Data da aplicação é obrigatória")
    @Column(name = "dt_aplicacao", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dataAplicacao;

    @Column(name = "ds_tipo", length = 50)
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}