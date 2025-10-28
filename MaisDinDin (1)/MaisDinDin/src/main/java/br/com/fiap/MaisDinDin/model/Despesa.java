package br.com.fiap.MaisDinDin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "T_DESPESA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DESPESA")
    @SequenceGenerator(name = "SEQ_DESPESA", sequenceName = "SEQ_DESPESA", allocationSize = 1)
    @Column(name = "id_despesa")
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(name = "ds_despesa", nullable = false, length = 200)
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(name = "vl_despesa", nullable = false)
    private Double valor;

    @NotNull(message = "Data é obrigatória")
    @Column(name = "dt_despesa", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate data;

    @Column(name = "ds_categoria", length = 50)
    private String categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}