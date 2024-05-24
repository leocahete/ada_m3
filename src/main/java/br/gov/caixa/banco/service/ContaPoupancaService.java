package br.gov.caixa.banco.service;

import br.gov.caixa.banco.model.ContaPoupanca;
import br.gov.caixa.banco.service.consultaSaldo.ConsultaSaldo;
import br.gov.caixa.banco.service.deposito.Deposito;
import br.gov.caixa.banco.service.saque.SaquePFImpl;
import br.gov.caixa.banco.service.transferencia.TransferenciaPFImpl;

public class ContaPoupancaService implements ConsultaSaldo<ContaPoupanca>, SaquePFImpl<ContaPoupanca>,
        Deposito<ContaPoupanca>, TransferenciaPFImpl<ContaPoupanca> {
}
