package br.com.fiap.MaisDinDin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "T_RECEITA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECEITA")
    @SequenceGenerator(name = "SEQ_RECEITA", sequenceName = "SEQ_RECEITA", allocationSize = 1)
    @Column(name = "id_receita")
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(name = "ds_receita", nullable = false, length = 200)
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(name = "vl_receita", nullable = false)
    private Double valor;

    @NotNull(message = "Data é obrigatória")
    @Column(name = "dt_receita", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate data;

    @Column(name = "ds_categoria", length = 50)
    private String categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}