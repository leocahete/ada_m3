package br.gov.caixa.banco.service.saque;

import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.ClientePF;
import br.gov.caixa.banco.model.Conta;

import java.math.BigDecimal;

public interface SaquePFImpl<T extends Conta> extends Saque<ClientePF, T>{


    @Override
    default void sacar(ClientePF cliente, T conta, BigDecimal valor) throws ValorInvalidoException, SaldoInsuficienteException {
        if(valor.compareTo(BigDecimal.ZERO)<1) {
            throw new ValorInvalidoException("Valor menor que zero ou igual a zero");
        }
        if(conta.getSaldo().compareTo(valor)<0) {
            throw new SaldoInsuficienteException();
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
    }
}
