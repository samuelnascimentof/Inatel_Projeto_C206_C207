package br.inatel.C206L4.Model.Funcionario;

import br.inatel.C206L4.Model.Cliente;
import br.inatel.C206L4.Model.Conta;
import br.inatel.C206L4.Model.Endereco;
import br.inatel.C206L4.Model.Telefone;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

public class Gerente extends Funcionario {

    private Collection<Cliente> clientesGerenciados = new HashSet<>();
    private String setor;
    private int senha;

    public Gerente(String nome, String sobrenome, long cpf, LocalDate dataNascimento, Endereco endereco, Telefone[] contatos) {
        super(nome, sobrenome, cpf, dataNascimento, endereco, contatos);
    }

    public Conta abrirConta(Cliente cliente) {
        return null;
    }

    public void encerrarConta(Cliente cliente){

    }

    public Collection<Cliente> getClientesGerenciados() {
        return clientesGerenciados;
    }
}
