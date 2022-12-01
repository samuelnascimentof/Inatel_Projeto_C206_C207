package br.inatel.C206L4.Model;

import br.inatel.C206L4.Model.Funcionario.Gerente;

import java.time.LocalDate;

public class Cliente extends Pessoa implements Autenticavel {

    private LocalDate dataInicio;
    private Gerente gerente;
    private int senha;

    public Cliente(String nome, String sobrenome, long cpf, LocalDate dataNascimento, Endereco endereco, Telefone[] contatos) {
        super(nome, sobrenome, cpf, dataNascimento, endereco, contatos);
        this.senha = senha;
    }

    public void solicitarAberturaDeConta() {

    }

    public void encerrarConta(){

    }

    public void pedirAumentoLimite(double quantia) {

    }

    @Override
    public boolean autenticar(int senha) {
        return this.senha == senha;
    }

}
