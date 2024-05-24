package br.gov.caixa.banco.service.investimento;

import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.ClientePF;
import br.gov.caixa.banco.model.ContaCorrente;
import br.gov.caixa.banco.model.ContaInvestimento;

import java.math.BigDecimal;

public interface InvestirPFImpl extends Investir<ClientePF> {

    BigDecimal RENDIMENTO = BigDecimal.valueOf(0.01);

    @Override
    default ContaInvestimento investir(ClientePF cliente, ContaCorrente conta, BigDecimal valor) throws ValorInvalidoException, SaldoInsuficienteException {
        return null;
    }
}
