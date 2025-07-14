package com.exemplo.receptor;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP implements Runnable {
    private final int porta;

    public ServidorTCP(int porta) {
        this.porta = porta;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream input = socket.getInputStream();

                byte[] buffer = input.readAllBytes();
                imprimirRecebido("TCP", buffer);

                socket.close();
            }
        } catch (Exception e) {
            System.err.println("[TCP] Erro: " + e.getMessage());
        }
    }

    private void imprimirRecebido(String protocolo, byte[] dados) {
        String ascii = new String(dados);
        String hex = bytesParaHex(dados);

        System.out.println("[" + protocolo + "] Recebido:");
        System.out.println("ASCII: " + ascii);
        System.out.println("HEX:   " + hex);
        System.out.println(" ");
    }

    private String bytesParaHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
