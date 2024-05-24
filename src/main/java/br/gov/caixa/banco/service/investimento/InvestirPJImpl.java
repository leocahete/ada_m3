package br.gov.caixa.banco.service.investimento;

import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.ClientePJ;
import br.gov.caixa.banco.model.ContaCorrente;
import br.gov.caixa.banco.model.ContaInvestimento;

import java.math.BigDecimal;

public interface InvestirPJImpl extends Investir<ClientePJ> {

    BigDecimal RENDIMENTO = BigDecimal.valueOf(0.02);

    @Override
    default ContaInvestimento investir(ClientePJ cliente, ContaCorrente conta, BigDecimal valor) throws ValorInvalidoException, SaldoInsuficienteException {
        return null;
    }


}
