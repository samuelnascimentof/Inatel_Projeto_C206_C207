package br.inatel.C206L4.Model.Pessoa.Funcionario;

import br.inatel.C206L4.Model.Endereco;
import br.inatel.C206L4.Model.Pessoa.Pessoa;
import br.inatel.C206L4.Model.Telefone;

import java.time.LocalDate;
import java.util.*;

public abstract class Funcionario extends Pessoa {
    private Date dataAdmissao;
    private int numeroCracha;
    private Date horarioEntrada;
    private Date horarioSaida;

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public int getNumeroCracha() {
        return numeroCracha;
    }

    public Date getHorarioEntrada() {
        return horarioEntrada;
    }

    public Date getHorarioSaida() {
        return horarioSaida;
    }

    public Funcionario(String nome, String sobrenome, long cpf, LocalDate dataNascimento, int senha, Endereco endereco, Telefone[] contatos) {
        super(nome, sobrenome, cpf, dataNascimento, senha, endereco, contatos);
    }

    public void baterPonto(){

    }

    public void pedirDemissao(){

    }
}
