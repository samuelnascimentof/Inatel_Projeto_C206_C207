package br.inatel.C206L4.Model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class Conta {
    private static int numeroDeContas;
    private Cliente titular;
    private Agencia agencia;
    private int numeroDaConta; // Auto generated
    private LocalDate dataAbertura; // Auto generated
    private double saldo;
    private Collection<Transacao> transacoes;
    private double limiteChequeEspecial;
    private double limiteCartaoDeCredito;

    public Conta(Cliente titular, Agencia agencia) {
        this.titular = titular;
        this.agencia = agencia;
        this.numeroDaConta = ThreadLocalRandom.current().nextInt(1000,10000);
        this.dataAbertura = LocalDate.now();
        this.saldo = 0;
        this.limiteChequeEspecial = 0;
        this.limiteCartaoDeCredito = 0;
        this.transacoes = new HashSet<>();
    }

    public void saca(double valor) {

    }

    public void deposita(double valor){

    }

    public void transfere(double valor, Conta contaDestino) {

    }
}
