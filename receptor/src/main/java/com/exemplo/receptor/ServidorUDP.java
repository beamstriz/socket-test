package com.exemplo.receptor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorUDP implements Runnable {
    private final int porta;

    public ServidorUDP(int porta) {
        this.porta = porta;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(porta)) {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacoteRecebido);

                byte[] dados = new byte[pacoteRecebido.getLength()];
                System.arraycopy(pacoteRecebido.getData(), 0, dados, 0, pacoteRecebido.getLength());

                imprimirRecebido("UDP", dados);

                byte[] ack = "ACK".getBytes();
                InetAddress endereco = pacoteRecebido.getAddress();
                int portaRemota = pacoteRecebido.getPort();

                DatagramPacket resposta = new DatagramPacket(ack, ack.length, endereco, portaRemota);
                socket.send(resposta);
            }

        } catch (Exception e) {
            System.err.println("[UDP] Erro: " + e.getMessage());
        }
    }

    private void imprimirRecebido(String protocolo, byte[] dados) {
        String ascii = new String(dados);
        String hex = bytesParaHex(dados);

        System.out.println("[" + protocolo + "] Recebido:");
        System.out.println("ASCII: " + ascii);
        System.out.println("HEX:   " + hex);
        System.out.println("--------------------------------");
    }

    private String bytesParaHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
