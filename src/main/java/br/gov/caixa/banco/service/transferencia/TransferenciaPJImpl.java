package br.gov.caixa.banco.service.transferencia;
import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.ClientePJ;
import br.gov.caixa.banco.model.Conta;

import java.math.BigDecimal;

public interface TransferenciaPJImpl <T extends Conta> extends Transferencia<ClientePJ,T>{

    //Taxa de 5% para PJ sobre Transferência
    BigDecimal TAXA = BigDecimal.valueOf(0.05);

    default void transferir(ClientePJ cliente, T contaOrigem, BigDecimal valor, Conta contaDestino)throws ValorInvalidoException, SaldoInsuficienteException {
        if (valor.compareTo(BigDecimal.ZERO)<1){
            throw new ValorInvalidoException("Valor menor que zero ou igual a zero");
        }
        BigDecimal taxa = valor.multiply(TAXA);
        if(contaOrigem.getSaldo().compareTo(valor.add(taxa))<0){
            throw new SaldoInsuficienteException();
        }
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(taxa));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));
    }
}
