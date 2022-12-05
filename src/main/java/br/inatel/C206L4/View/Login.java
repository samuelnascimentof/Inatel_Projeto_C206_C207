package br.inatel.C206L4.View;

import br.inatel.C206L4.Controller.LoginController;
import br.inatel.C206L4.Util.ConsoleUtil;

import java.util.Scanner;

public class Login {
    private LoginController controller;
    private Scanner scanner;
    private long cpf;
    private int senha;

    public Login() {
        controller = new LoginController(this);
        scanner = new Scanner(System.in);
        this.loginScreen();
    }

    public void loginScreen(){
        ConsoleUtil.clearConsole();
        System.out.println("################################################################################");
        System.out.println("#                     Banco Stack Trace - Seja bem vindo!                      #");
        System.out.println("################################################################################");
        System.out.println("|                                                                               ");
        System.out.println("| Digite seu CPF e sua Senha para logar:                                        ");
        System.out.println("|                                                                               ");
          System.out.print("| CPF (somente números): ");
        cpf = scanner.nextLong();
          System.out.print("| Senha: ");
        senha = scanner.nextInt();

        while (!controller.logar(cpf, senha)) {
            ConsoleUtil.clearConsole();
            System.out.println("|-------------------------------------------------------------------------------");
            System.out.println("| CPF ou Senha inválidos!                                                       ");
            System.out.println("|                                                                               ");
            System.out.println("| Digite seu CPF e sua Senha para logar:                                        ");
            System.out.println("|                                                                               ");
              System.out.print("| CPF (somente números): ");
            cpf = scanner.nextLong();
              System.out.print("| Senha: ");
            senha = scanner.nextInt();

        }
    }
}













