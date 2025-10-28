package br.com.fiap.MaisDinDin.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "T_CONTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTA")
    @SequenceGenerator(name = "SEQ_CONTA", sequenceName = "SEQ_CONTA", allocationSize = 1)
    @Column(name = "id_conta")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // Uma conta pertence a um usuário
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nr_agencia", length = 10)
    private String agencia;

    @Column(name = "nr_conta", nullable = false, length = 20)
    private String numeroConta;

    @Column(name = "vl_saldo")
    private Double saldo = 0.0;
}