package br.gov.caixa.banco.service.investimento;

import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.*;

import java.math.BigDecimal;

public interface InvestirPJImpl<T extends Conta> extends Investir<ClientePJ, T>{

    //Rendimento de 2% para PJ no momento do investimento
    BigDecimal RENDIMENTO = BigDecimal.valueOf(0.02);

    @Override
    default ContaInvestimento investir(ClientePJ cliente, ContaCorrente conta, BigDecimal valor) throws ValorInvalidoException, SaldoInsuficienteException {
        return null;
    }


}
