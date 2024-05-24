package br.gov.caixa.banco.service;

import br.gov.caixa.banco.model.ContaInvestimento;
import br.gov.caixa.banco.service.consultaSaldo.ConsultaSaldo;
import br.gov.caixa.banco.service.deposito.Deposito;
import br.gov.caixa.banco.service.investimento.InvestirPFImpl;
import br.gov.caixa.banco.service.saque.SaquePFImpl;
import br.gov.caixa.banco.service.transferencia.TransferenciaPFImpl;

public class ContaInvestimentoPFService implements SaquePFImpl<ContaInvestimento>, ConsultaSaldo<ContaInvestimento>,
        Deposito<ContaInvestimento>, TransferenciaPFImpl<ContaInvestimento>, InvestirPFImpl {
}
