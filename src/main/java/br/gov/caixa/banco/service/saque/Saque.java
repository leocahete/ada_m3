package br.gov.caixa.banco.service.saque;

import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.Cliente;
import br.gov.caixa.banco.model.Conta;

import java.math.BigDecimal;

public interface Saque<T extends Cliente, S extends Conta> {

    void sacar(T cliente, S conta, BigDecimal valor) throws ValorInvalidoException, SaldoInsuficienteException;
}
