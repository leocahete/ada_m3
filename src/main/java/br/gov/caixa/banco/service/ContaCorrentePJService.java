package br.gov.caixa.banco.service;

import br.gov.caixa.banco.model.ContaCorrente;
import br.gov.caixa.banco.service.consultaSaldo.ConsultaSaldo;
import br.gov.caixa.banco.service.deposito.Deposito;
import br.gov.caixa.banco.service.saque.SaquePJImpl;
import br.gov.caixa.banco.service.transferencia.TransferenciaPJImpl;

public class ContaCorrentePJService implements ConsultaSaldo<ContaCorrente>, Deposito<ContaCorrente>,
        SaquePJImpl<ContaCorrente>, TransferenciaPJImpl<ContaCorrente> {
}
