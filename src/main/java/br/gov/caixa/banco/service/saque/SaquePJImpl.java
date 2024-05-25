package br.gov.caixa.banco.service.saque;

import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.ClientePJ;
import br.gov.caixa.banco.model.Conta;

import java.math.BigDecimal;


public interface SaquePJImpl<T extends Conta> extends Saque<ClientePJ, T>{

    //Taxa de 5% para PJ sobre Saque
    BigDecimal TAXA = BigDecimal.valueOf(0.05);

    @Override
    default void sacar(ClientePJ clientePJ, T conta, BigDecimal valor) throws ValorInvalidoException, SaldoInsuficienteException {
        BigDecimal taxa = valor.multiply(TAXA);
        if(valor.compareTo(BigDecimal.ZERO)<1) {
            throw new ValorInvalidoException("Valor menor que zero ou igual a zero");
        }
        if(conta.getSaldo().compareTo(valor.add(taxa))<0) {
            throw new SaldoInsuficienteException();
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        conta.setSaldo(conta.getSaldo().subtract(taxa));
    }
}
