package br.gov.caixa.banco.exception;

public class SaldoInsuficienteException extends Exception{

    public SaldoInsuficienteException() {
        super("Saldo insuficiente!");
    }
}
