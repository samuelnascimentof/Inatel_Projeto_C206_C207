package br.inatel.C206L4.Model;

public class Telefone {
    private long numeroComDDD;
    private boolean ehWhatsApp;

    public Telefone(long numero, boolean ehWhatsApp) {
        this.numeroComDDD = numero;
        this.ehWhatsApp = ehWhatsApp;
    }

    public long getNumeroComDDD() {
        return numeroComDDD;
    }

    public boolean isWhatsApp() {
        return ehWhatsApp;
    }

    @Override
    public String toString() {

        if (this.ehWhatsApp){
            return ((Long) numeroComDDD).toString().concat(" (WhatsApp)");
        } else {
            return ((Long) numeroComDDD).toString();
        }
    }
}
