package br.gov.caixa.banco.service.consultaSaldo;



import br.gov.caixa.banco.model.Conta;

import java.math.BigDecimal;

public interface ConsultaSaldo<T extends Conta> {

    default BigDecimal consultarSaldo(T conta) {
        return conta.getSaldo();
    }

}
