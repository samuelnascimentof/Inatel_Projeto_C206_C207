package br.inatel.C206L4.Model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {
    private String id;

    LocalDateTime data;
    private String contaOrigem;
    private String contaDestino;
    private double valor;
    public Transacao(String contaOrigem, String contaDestino, double valor) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.id = this.generateId(45);
        this.data = LocalDateTime.now();
    }
    private String generateId(int size) {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            int index = (int)(alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return  "Data: " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + System.lineSeparator() +
                "Hora: " + data.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + System.lineSeparator() +
                "Conta origem: " + this.contaOrigem.split("\\|")[0] + " - " + this.contaOrigem.split("\\|")[1] + System.lineSeparator() +
                "Conta destino: " + this.contaDestino.split("\\|")[0] + " - " + this.contaDestino.split("\\|")[1] + System.lineSeparator() +
                "Valor: R$" + this.valor + System.lineSeparator() +
                "ID transação: " + this.id + System.lineSeparator();
    }
}
