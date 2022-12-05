package br.inatel.C206L4.Model.Pessoa;

import br.inatel.C206L4.Model.Endereco;
import br.inatel.C206L4.Model.Telefone;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public abstract class Pessoa {
    private String nome;
    private String sobrenome;
    private long cpf;
    private LocalDate dataNascimento;

    private Endereco endereco;

    private Map<Integer, Telefone> contatos;
    protected int senha;

    public Pessoa(String nome, String sobrenome, long cpf, LocalDate dataNascimento, int senha, Endereco endereco, Telefone[] contatos) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.senha = senha;

        if (contatos != null) {
            this.contatos = new HashMap<>();
            int i = 1;
            for (Telefone contato : contatos) {
                this.contatos.put(i, contato);
                i++;
            }
        }
    }

    public void adicionarTelefone(Telefone telefone){
        int i = this.contatos.size() + 1;
        while(true){
            if (this.contatos.containsKey(i)) {
                i++;
            } else {
                this.contatos.put(i, telefone);
                break;
            }
        }
    }

    public void removerTelefone(Integer chave) {
        if(this.contatos.containsKey(chave)){
            contatos.remove(chave);
            System.out.println("Contato removido com sucesso!");
            // Cria um novo mapa para manter as chaves dos telefones em sequencia
            // TODO Improve
            Map<Integer, Telefone> tempMap = new HashMap<>();
            int i = 1;
            for (Telefone telefone : this.contatos.values()) {
                tempMap.put(i, telefone);
                i++;
            }
            this.contatos = tempMap;

        } else {
            System.out.println("Opção inválida!");;
        }
    }

    public int getIdade(){
        LocalDate start = LocalDate.of(dataNascimento.getYear(), dataNascimento.getMonth(), dataNascimento.getDayOfMonth());
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        long idade = ChronoUnit.YEARS.between(start, end);
        return (int) idade;
    }

    public Map<Integer, Telefone> getContatos(){
        return this.contatos;
    }

    public String listarContatos() {
        StringBuilder listaContatos = new StringBuilder();

        this.contatos.forEach((chave, contato) -> {
            if(contato.isWhatsApp()){
                listaContatos.append(chave).append(" - ").append(contato.getNumeroComDDD()).append(" (WhatsApp)").append(System.lineSeparator());
            } else {
                listaContatos.append(chave).append(" - ").append(contato.getNumeroComDDD()).append(System.lineSeparator());
            }
        });

        return listaContatos.toString();
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setContatos(Telefone[] contatos) {
        if (contatos != null) {
            this.contatos = new HashMap<>();
            int i = 1;
            for (Telefone contato : contatos) {
                this.contatos.put(i, contato);
                i++;
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public long getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public int getSenha() {
        return senha;
    }

    public void mostrarInformacoes() {
        System.out.println("\nNome: " + this.nome);
        System.out.println("Sobrenome: " + this.sobrenome);
        System.out.println("CPF: " + this.cpf);
        System.out.println("Data de nascimento: " + dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Endereco: \n" + this.endereco);
        System.out.println("Contatos: \n" + this.listarContatos());
    }
}
