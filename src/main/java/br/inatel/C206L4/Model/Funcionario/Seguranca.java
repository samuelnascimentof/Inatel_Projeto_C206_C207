package br.inatel.C206L4.Model.Funcionario;

import br.inatel.C206L4.Model.Endereco;
import br.inatel.C206L4.Model.Telefone;

import java.time.LocalDate;

public class Seguranca extends Funcionario {
    public Seguranca(String nome, String sobrenome, long cpf, LocalDate dataNascimento, Endereco endereco, Telefone[] contatos) {
        super(nome, sobrenome, cpf, dataNascimento, endereco, contatos);
    }

    public void destravarPortaGiratoria() {

    }
}
