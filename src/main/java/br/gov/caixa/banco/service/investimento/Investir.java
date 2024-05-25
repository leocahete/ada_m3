package br.gov.caixa.banco.service.investimento;

import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.Cliente;
import br.gov.caixa.banco.model.Conta;
import br.gov.caixa.banco.model.ContaCorrente;
import br.gov.caixa.banco.model.ContaInvestimento;


import java.math.BigDecimal;

public interface Investir<T extends Cliente, S extends Conta>{

    ContaInvestimento investir(T cliente, ContaCorrente conta, BigDecimal valor) throws ValorInvalidoException, SaldoInsuficienteException;
}
