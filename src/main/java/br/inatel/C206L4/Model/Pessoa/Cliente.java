package br.inatel.C206L4.Model.Pessoa;

import br.inatel.C206L4.Model.Autenticavel;
import br.inatel.C206L4.Model.Conta;
import br.inatel.C206L4.Model.Endereco;
import br.inatel.C206L4.Model.Pessoa.Funcionario.Gerente;
import br.inatel.C206L4.Model.Telefone;

import java.time.LocalDate;

public class Cliente extends Pessoa implements Autenticavel {

    private LocalDate dataInicio;

    private Gerente gerente;

    private Conta conta;

    public Cliente(String nome, String sobrenome, long cpf, LocalDate dataNascimento, int senha, Endereco endereco, Telefone[] contatos) {
        super(nome, sobrenome, cpf, dataNascimento, senha, endereco, contatos);
    }
    public void solicitarAberturaDeConta() {

    }

    public void encerrarConta(){

    }

    public void pedirAumentoLimite(double quantia) {

    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    @Override
    public boolean autenticar(int senha) {
        return super.senha == senha;
    }

}
