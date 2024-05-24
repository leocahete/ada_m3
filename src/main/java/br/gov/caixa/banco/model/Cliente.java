package br.gov.caixa.banco.model;


import br.gov.caixa.banco.enums.StatusEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(force = true)
@DiscriminatorColumn(name = "tipo_cliente" , discriminatorType = DiscriminatorType.INTEGER)
@ToString
public abstract class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(columnDefinition = "UUID", nullable = false)
    private UUID uuid;

    @Column
    private String nome;
    @Column
    private LocalDate dataCadastro;
    @Column
    private StatusEnum status;

}
