package br.gov.caixa.banco.service;

import br.gov.caixa.banco.model.ContaInvestimento;
import br.gov.caixa.banco.service.consultaSaldo.ConsultaSaldo;
import br.gov.caixa.banco.service.deposito.Deposito;
import br.gov.caixa.banco.service.investimento.InvestirPJImpl;
import br.gov.caixa.banco.service.saque.SaquePJImpl;
import br.gov.caixa.banco.service.transferencia.TransferenciaPJImpl;

public class ContaInvestimentoPJService implements SaquePJImpl<ContaInvestimento>, ConsultaSaldo<ContaInvestimento>,
        Deposito<ContaInvestimento>, TransferenciaPJImpl<ContaInvestimento>, InvestirPJImpl {
}
