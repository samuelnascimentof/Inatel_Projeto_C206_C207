package br.inatel.C206L4.View;

import br.inatel.C206L4.Controller.MainMenuClienteController;
import br.inatel.C206L4.Model.Cliente;
import br.inatel.C206L4.Model.Conta;

import java.util.Scanner;

public class MainMenuCliente {
    private MainMenuClienteController controller;
    Cliente cliente;
    Conta conta;
    Scanner scanner;
    int operacao;

    public MainMenuCliente(long cpf) {
        this.controller = new MainMenuClienteController(this);
        recuperarDados(cpf);
        scanner = new Scanner(System.in);
    }

    private void mainMenuClienteScreen() {
        System.out.println("################################################################################");
        System.out.println("#                                Menu Principal                                #");
        System.out.println("################################################################################");
        //Exibe informações sobre o cliente e a conta que estão logados
        System.out.println("|                                                                               ");
        System.out.println("| Operações disponíveis:                                                        ");
        System.out.println("|                                                                               ");
        System.out.println("| 1 - Saque                                                                     ");
        System.out.println("| 2 - Depósito                                                                  ");
        System.out.println("| 3 - Transferência                                                             ");
        System.out.println("|                                                                               ");
          System.out.print("| Digite o número da operação desejada: ");
          operacao = scanner.nextInt();
    }

    private void recuperarDados(long cpf) {
        // Busca pelo cliente e pela conta relacionados ao cpf, e atribui ao cliente e conta atuais


//        this.cliente = cliente;
//        this.conta = conta;
    }

}

