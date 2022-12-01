package br.inatel.C206L4.Model;

import br.inatel.C206L4.Model.Funcionario.Funcionario;

import java.util.*;

public class Agencia {
    private static int numeroDeAgencias = 0;
    private final Endereco endereco;
    private final int codigoAgencia;
    private Collection<Funcionario> funcionarios = new HashSet<>();

    public Agencia(Endereco endereco, int codigoAgencia) {
        this.endereco = endereco;
        this.codigoAgencia = codigoAgencia;
        numeroDeAgencias++;
    }

    public void contratar(Funcionario funcionario){
        funcionarios.add(funcionario);
    }

    public void demitir(Funcionario funcionario){
        funcionarios.remove(funcionario);
        // TODO Adicionar validações e exceções
    }

    public static int getNumeroDeAgencias() {
        return numeroDeAgencias;
    }

    public int getNumeroDeFuncionarios() {
        return funcionarios.size();
    }

    public int getCodigoAgencia() {
        return codigoAgencia;
    }

    public Endereco getEndereco() {
        return endereco;
    }

}
