package br.inatel.C206L4.View;

import br.inatel.C206L4.Controller.MainMenuClienteController;
import br.inatel.C206L4.DAO.DatabaseController;
import br.inatel.C206L4.Exception.ContaInexistenteException;
import br.inatel.C206L4.Exception.SaldoInsuficienteException;
import br.inatel.C206L4.Model.Pessoa.Cliente;
import br.inatel.C206L4.Model.Conta;

import java.util.Scanner;

public class MainMenuCliente {
    private MainMenuClienteController controller;
    private Login loginView;
    private Cliente cliente;
    private Conta conta;
    private Scanner scanner;
    private int operacao;

    public MainMenuCliente(Cliente cliente, Login loginView) {
        this.controller = new MainMenuClienteController(this);
        this.cliente = cliente;
        scanner = new Scanner(System.in);
        this.loginView = loginView;
        mainMenuClienteScreen();
    }

    private void mainMenuClienteScreen() {
        do {
            System.out.println("\n\n\n################################################################################");
            System.out.println("#                                Menu Principal                                #");
            System.out.println("################################################################################");
            System.out.println("| Olá, " + this.cliente.getNome());
            System.out.println("| Dados da sua conta:                                                           ");
            System.out.println("| Agência: " + this.cliente.getConta().getAgencia().getCodigoAgencia());
            System.out.println("| Número da conta: " + this.cliente.getConta().getNumeroDaConta());
            System.out.println("| Saldo atual: R$" + String.format("%.2f", this.cliente.getConta().getSaldo()));
            System.out.println("| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            System.out.println("| Operações disponíveis:                                                        ");
            System.out.println("| 1 - Saque                                                                     ");
            System.out.println("| 2 - Depósito                                                                  ");
            System.out.println("| 3 - Transferência                                                             ");
            System.out.println("| 4 - Extrato                                                                   ");
            System.out.println("| 5 - Encerrar conta                                                            ");
            System.out.println("|                                                                               ");
            System.out.println("| 0 - Sair                                                                      ");
            System.out.println("|                                                                               ");
            double valor = 0;
            System.out.print("| Digite o número da operação desejada: ");
            operacao = scanner.nextInt();

            switch (operacao){
                case 1:
                    System.out.print("Digite o valor que deseja sacar: ");
                    valor = scanner.nextDouble();
                    if (valor > 0) {
                        try {
                            cliente.getConta().saca(valor);
                        } catch (SaldoInsuficienteException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            System.out.println("Valor em conta: R$" + String.format("%.2f", this.cliente.getConta().getSaldo()));
                        }
                    } else {
                        System.out.println("Valor inválido!");
                    }
                    break;
                case 2:
                    System.out.print("Digite o valor para depósito: ");
                    valor = scanner.nextDouble();
                    if (valor > 0) {
                        cliente.getConta().deposita(valor);
                        System.out.println("Valor em conta: R$" + String.format("%.2f", this.cliente.getConta().getSaldo()));
                    } else {
                        System.out.println("Valor inválido!");
                    }
                    break;
                case 3:
                    System.out.print("Digite o valor a ser transferido: ");
                    valor = scanner.nextDouble();
                    if (valor > 0) {
                        System.out.print("Digite o número da conta de destino: ");
                        int conta = scanner.nextInt();
                        System.out.print("Digite o número da agência de destino: ");
                        int agencia = scanner.nextInt();
                        try {
                            cliente.getConta().transfere(valor, agencia, conta);
                        } catch (ContaInexistenteException | SaldoInsuficienteException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            System.out.println("Valor em conta: R$" + String.format("%.2f", this.cliente.getConta().getSaldo()));
                        }
                    } else {
                        System.out.println("Valor inválido!");
                    }
                    break;
                case 4:
                    cliente.getConta().gerarExtrato();
                    break;
                case 5:
                    DatabaseController.deleteCliente(cliente);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (operacao != 0);

        System.out.println("Saindo do sistema...");
        loginView.loginScreen();

    }


}

