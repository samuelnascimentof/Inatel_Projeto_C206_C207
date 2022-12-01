package br.inatel.C206L4.Model.Funcionario;

import br.inatel.C206L4.Model.Cliente;
import br.inatel.C206L4.Model.Conta;
import br.inatel.C206L4.Model.Endereco;
import br.inatel.C206L4.Model.Telefone;

import java.time.LocalDate;

public class Caixa extends Funcionario {

    private int numeroGuiche;

    public Caixa(String nome, String sobrenome, long cpf, LocalDate dataNascimento, Endereco endereco, Telefone[] contatos) {
        super(nome, sobrenome, cpf, dataNascimento, endereco, contatos);
    }

    public void deposita(double valor, Conta conta) {

    }

    public void saca(double valor, Conta conta, Cliente cliente) {

    }

    public void transfere(double valor, Conta contaOrigem, Conta contaDestino, Cliente cliente) {

    }

    public int getNumeroGuiche() {
        return numeroGuiche;
    }
}
