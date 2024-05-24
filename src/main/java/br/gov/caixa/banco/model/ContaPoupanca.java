package br.gov.caixa.banco.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("3")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class ContaPoupanca extends Conta {

}
