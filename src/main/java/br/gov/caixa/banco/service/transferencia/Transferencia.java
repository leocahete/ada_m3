package br.gov.caixa.banco.service.transferencia;

import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.Cliente;
import br.gov.caixa.banco.model.Conta;

import java.math.BigDecimal;

public interface Transferencia <T extends Cliente, S extends Conta> {

    void transferir(T cliente, S contaOrigem, BigDecimal valor, Conta contaDestino) throws ValorInvalidoException, SaldoInsuficienteException;
}
