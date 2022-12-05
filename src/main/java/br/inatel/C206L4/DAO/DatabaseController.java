package br.inatel.C206L4.DAO;

import br.inatel.C206L4.Exception.AgenciaInexistenteException;
import br.inatel.C206L4.Exception.ContaInexistenteException;
import br.inatel.C206L4.Exception.PessoaInexistenteException;
import br.inatel.C206L4.Model.*;
import br.inatel.C206L4.Model.Pessoa.*;
import br.inatel.C206L4.Model.Pessoa.Funcionario.Caixa;
import br.inatel.C206L4.Model.Pessoa.Funcionario.Funcionario;
import br.inatel.C206L4.Model.Pessoa.Funcionario.Gerente;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DatabaseController {
    private static DatabaseConnection database = new DatabaseConnection();
    private Statement statement;
    private static ResultSet result;
    private static PreparedStatement pst;

    public static boolean insertFuncionario(Funcionario funcionario, int senha) {
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
            }

            // Insere endereço do funcionario
            insertEndereco(funcionario);

            // Insere contatos do funcionario
            insertContatos(funcionario);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir funcionario no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertCliente(Cliente cliente, Gerente gerente, int senha) {
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
            insertEndereco(cliente);

            // Insere contatos do cliente
            insertContatos(cliente);

            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir cliente no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static void insertEndereco(Pessoa pessoa) {
        try (Connection connection = database.connect()) {
            String sql = "INSERT INTO endereco(NomeRua, Numero, Complemento, Bairro, Cidade, Estado, Cep, Cliente_CPF) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1, pessoa.getEndereco().getRua());
            pst.setString(2, pessoa.getEndereco().getNumero());
            pst.setString(3, pessoa.getEndereco().getComplemento());
            pst.setString(4, pessoa.getEndereco().getBairro());
            pst.setString(5, pessoa.getEndereco().getCidade());
            pst.setString(6, pessoa.getEndereco().getEstado());
            pst.setString(7, pessoa.getEndereco().getCep());
            pst.setLong(8, pessoa.getCpf());
            pst.execute();
        } catch (Exception e) {
            System.out.println("Erro ao inserir endereço no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertContatos(Pessoa pessoa) {
        try (Connection connection = database.connect()) {
            for (Telefone telefone : pessoa.getContatos().values()) {
                String sql = "INSERT INTO telefone(Numero, EhWhatsApp, Cliente_CPF) VALUES (?, ?, ?)";
                pst = connection.prepareStatement(sql);
                pst.setLong(1, telefone.getNumeroComDDD());
                pst.setBoolean(2, telefone.isWhatsApp());
                pst.setLong(3, pessoa.getCpf());
                pst.execute();
            }
        } catch (Exception e) {
            System.out.println("Erro ao inserir contatos no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean insertConta(Conta conta) {
        try (Connection connection = database.connect()) {

            // Insere dados da transacao
            String sql = "INSERT INTO conta(NumeroConta, Agencia_CodigoAgencia, DataAbertura, Saldo, LimiteCheque, LimiteCartao, Cliente_CPF) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, conta.getNumeroDaConta());
            pst.setInt(2, conta.getAgencia().getCodigoAgencia());
            pst.setDate(3, Date.valueOf(conta.getDataAbertura()));
            pst.setFloat(4, ((Double) conta.getSaldo()).floatValue());
            pst.setFloat(5, ((Double) conta.getLimiteChequeEspecial()).floatValue());
            pst.setFloat(6, ((Double) conta.getLimiteCartaoDeCredito()).floatValue());
            pst.setLong(7, conta.getTitular().getCpf());
            pst.execute();

            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir conta no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertTransacao(Transacao transacao) {
        try (Connection connection = database.connect()) {

            // Insere dados da transacao
            String sql = "INSERT INTO transacao(idTransacaoBancaria, Data, ContaOrigem, ContaDestino, Valor, Conta_NumeroConta, Conta_Agencia_CodigoAgencia) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1, transacao.getId());
            pst.setTimestamp(2, Timestamp.valueOf(transacao.getData()));
            pst.setString(3, transacao.getContaOrigem());
            pst.setString(4, transacao.getContaDestino());
            pst.setFloat(5, ((Double) transacao.getValor()).floatValue());
            pst.setInt(6, Integer.parseInt(transacao.getContaOrigem().split("\\|")[1]));
            pst.setInt(7, Integer.parseInt(transacao.getContaOrigem().split("\\|")[0]));
            pst.execute();

            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir transação no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Pessoa buscarCPF(long cpf) throws PessoaInexistenteException {
        try (Connection connection = database.connect()) {

            // Busca cliente
            String sql = "SELECT * FROM cliente WHERE cpf = ?";
            pst = connection.prepareStatement(sql);
            pst.setLong(1, cpf);
            result = pst.executeQuery();

            if(result.next()) {
                Date dataInicio = result.getDate("DataInicio");
                long funcionarioCpf = result.getLong("Funcionario_CPF");
                Cliente cliente = new Cliente(
                        result.getString("Nome"),
                        result.getString("Sobrenome"),
                        result.getLong("CPF"),
                        result.getDate("DataNascimento").toLocalDate(),
                        result.getInt("Senha"),
                        buscarEnderecoCliente(cpf),
                        buscarContatosCliente(cpf)
                );
                cliente.setDataInicio(dataInicio.toLocalDate());
                cliente.setGerente((Gerente) buscarCPF(funcionarioCpf));
                cliente.setConta(buscarContaCliente(cliente));
                return cliente;
            } else {
                // Busca funcionario
                sql = "SELECT * FROM funcionario WHERE cpf = ?";
                pst = connection.prepareStatement(sql);
                pst.setLong(1, cpf);
                result = pst.executeQuery();

                if(result.next()) {
                    if(result.getString("Funcao").equals("Gerente")) {
                        String setor = result.getString("Setor");
                        // Cria um gerente
                        Gerente gerente = new Gerente(
                                result.getString("Nome"),
                                result.getString("Sobrenome"),
                                result.getLong("CPF"),
                                result.getDate("DataNascimento").toLocalDate(),
                                result.getInt("Senha"),
                                buscarEnderecoFuncionario(cpf),
                                buscarContatosFuncionario(cpf)
                        );
                        gerente.setClientesGerenciados(buscarClientesGerenciados(gerente));
                        gerente.setSetor(setor);
                        return gerente;
                    } else if (result.getString("Funcao").equals("Caixa")) {
                        int numeroGuiche = result.getInt("NumeroGuiche");
                        // Cria um caixa
                        Caixa caixa = new Caixa(
                                result.getString("Nome"),
                                result.getString("Sobrenome"),
                                result.getLong("CPF"),
                                result.getDate("DataNascimento").toLocalDate(),
                                result.getInt("Senha"),
                                buscarEnderecoFuncionario(cpf),
                                buscarContatosFuncionario(cpf)
                        );
                        caixa.setNumeroGuiche(numeroGuiche);
                        return caixa;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        throw new PessoaInexistenteException("CPF não encontrado em nosso banco de dados!");
    }

    private static Collection<Cliente> buscarClientesGerenciados(Gerente gerente) {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM cliente WHERE Funcionario_CPF = ?";
            pst = connection.prepareStatement(sql);
            pst.setLong(1, gerente.getCpf());
            result = pst.executeQuery();

            List<Cliente> clientes = new ArrayList<>();
            while (result.next()) {
                String nome = result.getString("Nome");
                String sobrenome = result.getString("Sobrenome");
                long cpf = result.getLong("CPF");
                LocalDate dataNascimento = result.getDate("DataNascimento").toLocalDate();
                int senha = result.getInt("Senha");
                LocalDate dataInico = result.getDate("DataInicio").toLocalDate();
                Cliente cliente = new Cliente(
                        nome,
                        sobrenome,
                        cpf,
                        dataNascimento,
                        senha,
                        null,
                        null
                );
                cliente.setDataInicio(dataInico);
                cliente.setGerente(gerente);
                clientes.add(cliente);
            }

            clientes.forEach(cliente -> {
                cliente.setEndereco(buscarEnderecoCliente(cliente.getCpf()));
                cliente.setContatos(buscarContatosCliente(cliente.getCpf()));
            });


            return clientes;
        } catch (Exception e) {
            System.out.println("Erro ao buscar clientes gerenciados no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Cliente buscarClientePelaConta(int agenciaDestino, int contaDestino) throws PessoaInexistenteException {
        try (Connection connection = database.connect()) {

            String sql = "SELECT Cliente_CPF FROM conta WHERE NumeroConta = ? AND Agencia_CodigoAgencia = ?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, contaDestino);
            pst.setInt(2, agenciaDestino);
            result = pst.executeQuery();

            if (result.next()) {
                return (Cliente) buscarCPF(result.getLong("Cliente_CPF"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente pela conta no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        throw new PessoaInexistenteException("Conta não encontada em nosso banco de dados.");
    }

    public static Conta buscarContaCliente(Cliente cliente) throws ContaInexistenteException {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM conta WHERE Cliente_CPF = ?";
            pst = connection.prepareStatement(sql);
            pst.setLong(1, cliente.getCpf());
            result = pst.executeQuery();

            if (result.next()) {
                int numeroConta = result.getInt("NumeroConta");
                int codigoAgencia = result.getInt("Agencia_CodigoAgencia");
                LocalDate dataAbertura = result.getDate("DataAbertura").toLocalDate();
                Double saldo = result.getDouble("Saldo");
                double limiteCheque = result.getDouble("LimiteCheque");
                double limiteCartao = result.getDouble("LimiteCartao");
                return new Conta(cliente, buscarAgencia(codigoAgencia), numeroConta, dataAbertura, saldo, limiteCheque, limiteCartao, buscarTransacoes(numeroConta));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente pela conta no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        throw new ContaInexistenteException("Conta não encontada em nosso banco de dados.");
    }

    public static Conta buscarConta(int agencia, int numero) throws ContaInexistenteException {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM conta WHERE NumeroConta = ? AND Agencia_CodigoAgencia = ?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, numero);
            pst.setInt(2, agencia);
            result = pst.executeQuery();

            if (result.next()) {
                int numeroConta = result.getInt("NumeroConta");
                int codigoAgencia = result.getInt("Agencia_CodigoAgencia");
                LocalDate dataAbertura = result.getDate("DataAbertura").toLocalDate();
                Double saldo = result.getDouble("Saldo");
                double limiteCheque = result.getDouble("LimiteCheque");
                double limiteCartao = result.getDouble("LimiteCartao");
                long cpfCliente = result.getLong("Cliente_CPF");
                return new Conta((Cliente) buscarCPF(cpfCliente), buscarAgencia(codigoAgencia), numeroConta, dataAbertura, saldo, limiteCheque, limiteCartao, buscarTransacoes(numeroConta));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar conta no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        throw new ContaInexistenteException("Conta não encontada em nosso banco de dados.");
    }

    private static Collection<Transacao> buscarTransacoes(int numeroConta) {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM transacao WHERE Conta_NumeroConta = ?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, numeroConta);
            result = pst.executeQuery();

            List<Transacao> transacoes = new ArrayList<>();
            while (result.next()) {
                int numAgenciaOrigem = Integer.parseInt(result.getString("ContaOrigem").split("\\|")[0]);
                int numContaOrigem = Integer.parseInt(result.getString("ContaOrigem").split("\\|")[1]);
                int numAgenciaDestino = Integer.parseInt(result.getString("ContaDestino").split("\\|")[0]);
                int numContaDestino = Integer.parseInt(result.getString("ContaDestino").split("\\|")[1]);
                double valor = result.getDouble("Valor");
                String id = result.getString("idTransacaoBancaria");
                LocalDateTime data = result.getTimestamp("Data").toLocalDateTime();

                Transacao transacao = new Transacao(numAgenciaOrigem + "|" + numContaOrigem, numAgenciaDestino + "|" + numContaDestino, valor);

                transacao.setId(id);
                transacao.setData(data);

                transacoes.add(transacao);
            }
            return transacoes;
        } catch (Exception e) {
            System.out.println("Erro ao buscar contatos do cliente no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Agencia buscarAgencia(int codigoAgencia) throws AgenciaInexistenteException {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM agencia WHERE CodigoAgencia = ?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, codigoAgencia);
            result = pst.executeQuery();

            if (result.next()) {
                return new Agencia(buscarEnderecoAgencia(codigoAgencia), codigoAgencia);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar agencia no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        throw new AgenciaInexistenteException("Agência não encontrada em nosso banco de dados.");
    }
    private static Endereco buscarEnderecoCliente(long cpf) {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM endereco WHERE Cliente_CPF = ?";
            pst = connection.prepareStatement(sql);
            pst.setLong(1, cpf);
            result = pst.executeQuery();

            Endereco endereco;
            if (result.next()) {
                if (result.getString("Complemento") != null) {
                    endereco = new Endereco(result.getString("NomeRua"), result.getString("Numero"),
                            result.getString("Complemento"), result.getString("Bairro"),
                            result.getString("Cidade"), result.getString("Estado"),
                            result.getString("Cep"));
                } else {
                    endereco = new Endereco(result.getString("NomeRua"), result.getString("Numero"),
                            result.getString("Bairro"), result.getString("Cidade"),
                            result.getString("Estado"), result.getString("Cep"));
                }
                return endereco;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar endereço do cliente no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static Telefone[] buscarContatosCliente(long cpf) {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM telefone WHERE Cliente_CPF = ?";
            pst = connection.prepareStatement(sql);
            pst.setLong(1, cpf);
            result = pst.executeQuery();

            List<Telefone> telefonesList = new ArrayList<>();
            int count = 0;
            while (result.next()) {
                Telefone telefone = new Telefone(result.getLong("Numero"), result.getBoolean("EhWhatsApp"));
                telefonesList.add(telefone);
                count++;
            }

            Telefone[] telefones = new Telefone[count];
            for (int i = 0; i < count; i++) {
                telefones[i] = telefonesList.get(i);
            }

            return telefones;
        } catch (Exception e) {
            System.out.println("Erro ao buscar contatos do cliente no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static Telefone[] buscarContatosFuncionario(long cpf) {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM telefone WHERE Funcionario_CPF = ?";
            pst = connection.prepareStatement(sql);
            pst.setLong(1, cpf);
            result = pst.executeQuery();

            List<Telefone> telefonesList = new ArrayList<>();
            int count = 0;
            while (result.next()) {
                Telefone telefone = new Telefone(result.getLong("Numero"), result.getBoolean("EhWhatsApp"));
                telefonesList.add(telefone);
                count++;
            }

            Telefone[] telefones = new Telefone[count];
            for (int i = 0; i < count; i++) {
                telefones[i] = telefonesList.get(i);
            }

            return telefones;
        } catch (Exception e) {
            System.out.println("Erro ao buscar contatos do cliente no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static Endereco buscarEnderecoFuncionario(long cpf) {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM endereco WHERE Funcionario_CPF = ?";
            pst = connection.prepareStatement(sql);
            pst.setLong(1, cpf);
            result = pst.executeQuery();

            Endereco endereco;
            if (result.next()) {
                if (result.getString("Complemento") != null) {
                    endereco = new Endereco(result.getString("NomeRua"), result.getString("Numero"),
                            result.getString("Complemento"), result.getString("Bairro"),
                            result.getString("Cidade"), result.getString("Estado"),
                            result.getString("Cep"));
                } else {
                    endereco = new Endereco(result.getString("NomeRua"), result.getString("Numero"),
                            result.getString("Bairro"), result.getString("Cidade"),
                            result.getString("Estado"), result.getString("Cep"));
                }
                return endereco;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar endereço do cliente no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static Endereco buscarEnderecoAgencia(int codigoAgencia) {
        try (Connection connection = database.connect()) {

            String sql = "SELECT * FROM endereco WHERE Agencia_CodigoAgencia = ?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, codigoAgencia);
            result = pst.executeQuery();

            Endereco endereco;
            if (result.next()) {
                if (result.getString("Complemento") != null) {
                    endereco = new Endereco(result.getString("NomeRua"), result.getString("Numero"),
                            result.getString("Complemento"), result.getString("Bairro"),
                            result.getString("Cidade"), result.getString("Estado"),
                            result.getString("Cep"));
                } else {
                    endereco = new Endereco(result.getString("NomeRua"), result.getString("Numero"),
                            result.getString("Bairro"), result.getString("Cidade"),
                            result.getString("Estado"), result.getString("Cep"));
                }
                return endereco;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar endereço da agencia no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteCliente(Cliente cliente) {
        try (Connection connection = database.connect()) {

            // Deleta o cliente
            String sql = "DELETE FROM cliente WHERE cpf = ?";
            pst = connection.prepareStatement(sql);
            pst.setLong(1, cliente.getCpf());
            pst.execute();

            return true;
        } catch (Exception e) {
            System.out.println("Erro excluir cliente do banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public static boolean updateConta(Conta conta) {
        try (Connection connection = database.connect()) {

            // Insere dados da transacao
            String sql = "UPDATE conta SET NumeroConta = ?, Agencia_CodigoAgencia = ?, DataAbertura = ?, Saldo = ?, LimiteCheque = ?, LimiteCartao = ? WHERE Cliente_CPF = ?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, conta.getNumeroDaConta());
            pst.setInt(2, conta.getAgencia().getCodigoAgencia());
            pst.setDate(3, Date.valueOf(conta.getDataAbertura()));
            pst.setFloat(4, ((Double) conta.getSaldo()).floatValue());
            pst.setFloat(5, ((Double) conta.getLimiteChequeEspecial()).floatValue());
            pst.setFloat(6, ((Double) conta.getLimiteCartaoDeCredito()).floatValue());
            pst.setLong(7, conta.getTitular().getCpf());
            pst.execute();

            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar conta no banco de dados. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
