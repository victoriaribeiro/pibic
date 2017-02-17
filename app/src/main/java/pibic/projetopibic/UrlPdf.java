package pibic.projetopibic;

import java.io.Serializable;


public class UrlPdf implements Serializable {
    public String nome;
    public String url;
    public String data;


    public UrlPdf(String nome, String url, String data) {
        this.nome = nome;
        this.url = url;
        this.data = data;
    }


    @Override
    public String toString() {

        return nome;
    }

    public String getNome() {
        return nome;
    }

    public String getUrl() {
        return url;
    }

    public String getData() {
        return data;
    }
}