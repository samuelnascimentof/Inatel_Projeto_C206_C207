package br.inatel.C206L4.DAO;

import br.inatel.C206L4.Model.Cliente;
import br.inatel.C206L4.Model.Funcionario.Caixa;
import br.inatel.C206L4.Model.Funcionario.Funcionario;
import br.inatel.C206L4.Model.Funcionario.Gerente;
import br.inatel.C206L4.Model.Telefone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

public class DatabaseController {
    private final DatabaseConnection database;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement pst;

    public DatabaseController() {
        this.database = new DatabaseConnection();
    }

    public boolean insertFuncionario(Funcionario funcionario, int senha) {
        try (Connection connection = database.connect()) {
            String sql;

            switch (funcionario.getClass().getSimpleName()) {
                case "Gerente":
                    // Insere dados do gerente
                    sql = "INSERT INTO funcionario(Nome, Sobrenome, CPF, DataNascimento, DataAdmissao, NumeroCracha, Funcao, " +
                            "ClientesGerenciados, Setor, Senha, Agencia_CodigoAgencia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pst = connection.prepareStatement(sql);
                    pst.setString(1, funcionario.getNome());
                    pst.setString(2, funcionario.getSobrenome());
                    pst.setLong(3, funcionario.getCpf());
                    pst.setDate(4, java.sql.Date.valueOf(funcionario.getDataNascimento()));
                    pst.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                    pst.setInt(6, new Random().nextInt(9999));
                    pst.setString(7, "Gerente");
                    pst.setInt(8, ((Gerente) funcionario).getClientesGerenciados().size());
                    pst.setString(9, "Gerência");
                    pst.setInt(10, senha);
                    pst.setInt(11, 8729);
                    pst.execute();
                    break;
                case "Caixa":
                    // Insere dados do caixa
                    sql = "INSERT INTO funcionario(Nome, Sobrenome, CPF, DataNascimento, DataAdmissao, NumeroCracha, Funcao, " +
                            "NumeroGuiche, Setor, Senha, Agencia_CodigoAgencia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pst = connection.prepareStatement(sql);
                    pst.setString(1, funcionario.getNome());
                    pst.setString(2, funcionario.getSobrenome());
                    pst.setLong(3, funcionario.getCpf());
                    pst.setDate(4, java.sql.Date.valueOf(funcionario.getDataNascimento()));
                    pst.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                    pst.setInt(6, new Random().nextInt(9999));
                    pst.setString(7, "Caixa");
                    pst.setInt(8, ((Caixa) funcionario).getNumeroGuiche());
                    pst.setString(9, "Atendimento");
                    pst.setInt(10, senha);
                    pst.setInt(11, 8729);
                    pst.execute();
                    break;

                case "Seguranca":
                    // Insere dados do segurança
                    sql = "INSERT INTO funcionario(Nome, Sobrenome, CPF, DataNascimento, DataAdmissao, NumeroCracha, Funcao, " +
                            "Setor, Senha, Agencia_CodigoAgencia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pst = connection.prepareStatement(sql);
                    pst.setString(1, funcionario.getNome());
                    pst.setString(2, funcionario.getSobrenome());
                    pst.setLong(3, funcionario.getCpf());
                    pst.setDate(4, java.sql.Date.valueOf(funcionario.getDataNascimento()));
                    pst.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                    pst.setInt(6, new Random().nextInt(9999));
                    pst.setString(7, "Segurança");
                    pst.setString(9, "Geral");
                    pst.setInt(10, senha);
                    pst.setInt(11, 8729);
                    pst.execute();
                    break;
            }

            // Insere endereço do funcionario
            sql = "INSERT INTO endereco(NomeRua, Numero, Complemento, Bairro, Cidade, Estado, Cep, Funcionario_CPF) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1, funcionario.getEndereco().getRua());
            pst.setString(2, funcionario.getEndereco().getNumero());
            pst.setString(3, funcionario.getEndereco().getComplemento());
            pst.setString(4, funcionario.getEndereco().getBairro());
            pst.setString(5, funcionario.getEndereco().getCidade());
            pst.setString(6, funcionario.getEndereco().getEstado());
            pst.setString(7, funcionario.getEndereco().getCep());
            pst.setLong(8, funcionario.getCpf());
            pst.execute();

            // Insere contatos do funcionario
            for (Telefone telefone : funcionario.getContatos().values()) {
                sql = "INSERT INTO telefone(Numero, EhWhatsApp, Funcionario_CPF) VALUES (?, ?, ?)";
                pst = connection.prepareStatement(sql);
                pst.setLong(1, telefone.getNumeroComDDD());
                pst.setBoolean(2, telefone.isWhatsApp());
                pst.setLong(3, funcionario.getCpf());
                pst.execute();
            }
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir funcionario no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertCliente(Cliente cliente, Gerente gerente, int senha) {
        try (Connection connection = database.connect()) {

            // Insere dados do cliente
            String sql = "INSERT INTO cliente(Nome, Sobrenome, CPF, DataNascimento, DataInicio, Senha, Funcionario_CPF) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getSobrenome());
            pst.setLong(3, cliente.getCpf());
            pst.setDate(4, java.sql.Date.valueOf(cliente.getDataNascimento()));
            pst.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
            pst.setInt(6, senha);
            pst.setLong(7, gerente.getCpf());
            pst.execute();

            // Insere endereço do cliente
            sql = "INSERT INTO endereco(NomeRua, Numero, Complemento, Bairro, Cidade, Estado, Cep, Cliente_CPF) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1, cliente.getEndereco().getRua());
            pst.setString(2, cliente.getEndereco().getNumero());
            pst.setString(3, cliente.getEndereco().getComplemento());
            pst.setString(4, cliente.getEndereco().getBairro());
            pst.setString(5, cliente.getEndereco().getCidade());
            pst.setString(6, cliente.getEndereco().getEstado());
            pst.setString(7, cliente.getEndereco().getCep());
            pst.setLong(8, cliente.getCpf());
            pst.execute();

            // Insere contatos do cliente
            for (Telefone telefone : cliente.getContatos().values()) {
                sql = "INSERT INTO telefone(Numero, EhWhatsApp, Cliente_CPF) VALUES (?, ?, ?)";
                pst = connection.prepareStatement(sql);
                pst.setLong(1, telefone.getNumeroComDDD());
                pst.setBoolean(2, telefone.isWhatsApp());
                pst.setLong(3, cliente.getCpf());
                pst.execute();
            }
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir cliente no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }







}
