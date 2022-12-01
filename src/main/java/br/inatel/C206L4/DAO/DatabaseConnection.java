package br.inatel.C206L4.DAO;

import br.inatel.C206L4.Model.Cliente;

import java.sql.*;

public class DatabaseConnection implements AutoCloseable {
    private Connection connection; // objeto responsável por fazer a conexão com mysql
    private final String user = "root";
    private final String password = "12345";
    private final String database = "bancodb";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/" + database +"?characterEncoding=utf-8";

    public Connection connect() {
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            System.out.println("Erro de conexão: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
