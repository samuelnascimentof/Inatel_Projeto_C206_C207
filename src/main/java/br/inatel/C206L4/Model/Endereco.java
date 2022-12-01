package br.inatel.C206L4.Model;

public class Endereco {

    private String rua;
    private String numero;
    private String complemento = "";
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(String rua, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public Endereco(String rua, String numero, String bairro, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    /**
     *
     * @return Vetor de String com 7 posições, contendo as seguintes informações em cada posição:
     * 0 - Nome da rua;
     * 1 - Número;
     * 2 - Complemento;
     * 3 - Bairro;
     * 4 - Cidade;
     * 5 - Estado;
     * 6 - CEP.
     */
    public String[] getEndereco() {
        String[] endereco = new String[7];

        endereco[0] = this.rua;
        endereco[1] = this.numero;
        endereco[2] = this.complemento;
        endereco[3] = this.bairro;
        endereco[4] = this.cidade;
        endereco[5] = this.estado;
        endereco[6] = this.cep;

        return endereco;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    @Override
    public String toString() {
        return "Rua: " + this.rua +
                "\nNumero: " + this.numero +
                "\nComplemento: " + this.complemento +
                "\nBairro: " + this.bairro +
                "\nCidade: " + this.cidade +
                "\nEstado: " + this.estado +
                "\nCEP: " + this.cep;
    }
}
