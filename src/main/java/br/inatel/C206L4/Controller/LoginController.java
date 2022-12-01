package br.inatel.C206L4.Controller;

import br.inatel.C206L4.Util.ConsoleUtil;
import br.inatel.C206L4.View.Login;
import br.inatel.C206L4.View.MainMenuCliente;

public class LoginController {
    private Login view;
    public LoginController(Login view) {
        this.view = view;
    }

    public boolean logar(long cpf, int senha) {
        // TODO Adicionar consulta de login ao banco de dados - pesquisa apenas pelo CPF

        if(/* Consulta ok */ false) {
            ConsoleUtil.clearConsole();

            // Redireciona para a tela principal passando o cpf
            MainMenuCliente menuCliente = new MainMenuCliente(cpf);
            return true;
        } else {
            return false;
        }
    }
}
