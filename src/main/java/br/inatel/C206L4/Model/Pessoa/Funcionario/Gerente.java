package br.inatel.C206L4.Model.Pessoa.Funcionario;

import br.inatel.C206L4.Model.Conta;
import br.inatel.C206L4.Model.Endereco;
import br.inatel.C206L4.Model.Pessoa.Cliente;
import br.inatel.C206L4.Model.Telefone;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

public class Gerente extends Funcionario {

    private Collection<Cliente> clientesGerenciados = new HashSet<>();
    private String setor;

    public Gerente(String nome, String sobrenome, long cpf, LocalDate dataNascimento, int senha, Endereco endereco, Telefone[] contatos) {
        super(nome, sobrenome, cpf, dataNascimento, senha, endereco, contatos);
    }

    public Conta abrirConta(Cliente cliente) {
        return null;
    }

    public void encerrarConta(Cliente cliente){

    }

    public Collection<Cliente> getClientesGerenciados() {
        return clientesGerenciados;
    }

    public void setClientesGerenciados(Collection<Cliente> clientesGerenciados) {
        this.clientesGerenciados = clientesGerenciados;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}
