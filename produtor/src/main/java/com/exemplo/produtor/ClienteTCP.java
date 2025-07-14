package com.exemplo.produtor;

import java.io.OutputStream;
import java.net.Socket;

public class ClienteTCP {
    private final String host;
    private final int porta;

    public ClienteTCP(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void enviar(byte[] dados) {
        try (Socket socket = new Socket(host, porta);
             OutputStream out = socket.getOutputStream()) {

            out.write(dados);
            out.flush();

        } catch (Exception e) {
            System.err.println("[TCP] Erro ao enviar dados: "+e.getMessage());
        }
    }

    public void fechar() {
    }
}
