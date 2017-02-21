package pibic.projetopibic;

import java.io.Serializable;


public class recado implements Serializable {
    public String mensagem;
    public String data;


    public recado(String mensagem, String data) {
        this.mensagem = mensagem;
        this.data = data;

    }

    public String getMensagem(){
        return mensagem;
    }
    public String getData(){
        return data;
    }

    @Override
    public String toString() {
        return mensagem + "\n" + data ;
    }


}