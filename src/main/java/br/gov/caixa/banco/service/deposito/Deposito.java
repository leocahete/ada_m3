package br.gov.caixa.banco.service.deposito;

import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.Conta;

import java.math.BigDecimal;

public interface Deposito<T extends Conta> {

    default void depositar(T conta, BigDecimal valor) throws ValorInvalidoException {
        if(valor.compareTo(BigDecimal.ZERO)<1){
            throw new ValorInvalidoException("Valor menor que zero ou igual a zero");
        }
            conta.setSaldo(conta.getSaldo().add(valor));
    }
}
