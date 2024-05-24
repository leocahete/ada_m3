package br.gov.caixa.banco.exception;

public class SaldoExistenteException extends Exception{

    public SaldoExistenteException() {
        super("Conta possui saldo!");

    }
}
