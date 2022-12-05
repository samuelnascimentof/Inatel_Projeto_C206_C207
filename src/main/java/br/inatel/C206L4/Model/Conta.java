package br.inatel.C206L4.Model;

import br.inatel.C206L4.DAO.DatabaseController;
import br.inatel.C206L4.Exception.PessoaInexistenteException;
import br.inatel.C206L4.Exception.ContaInexistenteException;
import br.inatel.C206L4.Exception.SaldoInsuficienteException;
import br.inatel.C206L4.Model.Pessoa.Cliente;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Conta {
    private static int numeroDeContas;

    private Cliente titular;

    private Agencia agencia;
    private int numeroDaConta;
    private LocalDate dataAbertura;

    private double saldo;

    private Collection<Transacao> transacoes;
    private double limiteChequeEspecial;

    private double limiteCartaoDeCredito;

    public Conta(Cliente titular, Agencia agencia, int numeroDaConta, LocalDate dataAbertura, double saldo, double limiteChequeEspecial, double limiteCartaoDeCredito, Collection<Transacao> transacoes) {
        this.titular = titular;
        this.agencia = agencia;
        this.numeroDaConta = numeroDaConta;
        this.dataAbertura = dataAbertura;
        this.saldo = saldo;
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.limiteCartaoDeCredito = limiteCartaoDeCredito;
        this.transacoes = transacoes;
    }
    public void saca(double valor) throws SaldoInsuficienteException {
        if (saldo >= valor) {
            saldo -= valor;
            DatabaseController.updateConta(this);
            System.out.println("Saque efetuado com sucesso!");
        } else {
            throw new SaldoInsuficienteException("Não foi possível efetuar o saque. Saldo insuficiente!");
        }
    }
    public void deposita(double valor) {
        this.saldo += valor;
        DatabaseController.updateConta(this);
        System.out.println("Depósito efetuado com sucesso!");
    }
    public void recebeTransferencia(double valor) {
        this.saldo += valor;
        DatabaseController.updateConta(this);
    }

    public void transfere(double valor, int agenciaDestino, int contaDestino) throws SaldoInsuficienteException, ContaInexistenteException {
        try {
            Cliente clienteDestino = DatabaseController.buscarClientePelaConta(agenciaDestino, contaDestino);
            if (this.saldo >= valor) {
                this.saldo -= valor;
                clienteDestino.getConta().recebeTransferencia(valor);
                Transacao transacao = new Transacao(this.toString(), clienteDestino.getConta().toString(), valor);
                transacoes.add(transacao);
                DatabaseController.insertTransacao(transacao);
                DatabaseController.updateConta(this);
                System.out.println("Transferência realizada com sucesso!");
            } else {
                throw new SaldoInsuficienteException("Não foi possível realizar a transferência! Saldo insuficiente.");
            }
        } catch (PessoaInexistenteException e) {
            throw new ContaInexistenteException("Não foi possível realizar a transferência! Conta de destino inexistente.");
        }
    }

    public void gerarExtrato() {
        System.out.println("\n");
        if (!this.transacoes.isEmpty()) {
            this.transacoes.forEach(System.out::println);
        } else {
            System.out.println("Não há transações a serem exibidas! ");
        }
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public int getNumeroDaConta() {
        return numeroDaConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public double getLimiteCartaoDeCredito() {
        return limiteCartaoDeCredito;
    }

    public Cliente getTitular() {
        return titular;
    }

    @Override
    public String toString() {
        String str = agencia.getCodigoAgencia() + "|" + numeroDaConta;
        return str;
    }
}
