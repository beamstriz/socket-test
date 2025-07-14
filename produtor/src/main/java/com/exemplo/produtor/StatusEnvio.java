package com.exemplo.produtor;

import org.dizitart.no2.objects.Id;

public class StatusEnvio {
    @Id
    private String nomeArquivo;
    private int ultimaLinhaEnviada;

    public StatusEnvio() {}

    public StatusEnvio(String nomeArquivo, int ultimaLinhaEnviada) {
        this.nomeArquivo = nomeArquivo;
        this.ultimaLinhaEnviada = ultimaLinhaEnviada;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getUltimaLinhaEnviada() {
        return ultimaLinhaEnviada;
    }

    public void setUltimaLinhaEnviada(int ultimaLinhaEnviada) {
        this.ultimaLinhaEnviada = ultimaLinhaEnviada;
    }
}
