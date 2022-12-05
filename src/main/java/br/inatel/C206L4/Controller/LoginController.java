package br.inatel.C206L4.Controller;

import br.inatel.C206L4.DAO.DatabaseController;
import br.inatel.C206L4.Exception.PessoaInexistenteException;
import br.inatel.C206L4.Model.Pessoa.Cliente;
import br.inatel.C206L4.Model.Pessoa.Funcionario.Gerente;
import br.inatel.C206L4.Model.Pessoa.Pessoa;
import br.inatel.C206L4.Util.ConsoleUtil;
import br.inatel.C206L4.View.Login;
import br.inatel.C206L4.View.MainMenuCliente;
import br.inatel.C206L4.View.MainMenuGerente;

public class LoginController {
    private Login view;
    public LoginController(Login view) {
        this.view = view;
    }

    public boolean logar(long cpf, int senha) {
        Pessoa pessoa = null;
        try {
            pessoa = DatabaseController.buscarCPF(cpf);
        } catch (PessoaInexistenteException e) {
            return false;
        }
        if(pessoa.getSenha() == senha) {
            ConsoleUtil.clearConsole();
            // Verifica se é cliente ou gerente
            String classe = pessoa.getClass().getSimpleName();
            if (classe.equals("Cliente")) {
                // Redireciona para a tela principal passando o cliente e a tela de login
                MainMenuCliente menuCliente = new MainMenuCliente((Cliente) pessoa, view);
                return true;
            } else if (pessoa.getClass().getSimpleName() == "Gerente") {
                // Redireciona para a tela principal passando o gerente e a tela de login
                MainMenuGerente menuGerente = new MainMenuGerente((Gerente) pessoa, view);
                return true;
            }
        }
        return false;
    }
}
