package com.exemplo.produtor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClienteUDP {
    private final String host;
    private final int porta;

    public ClienteUDP(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public boolean enviar(byte[] dados) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress endereco = InetAddress.getByName(host);
            DatagramPacket pacote = new DatagramPacket(dados, dados.length, endereco, porta);
            socket.send(pacote);

            byte[] buffer = new byte[1024];
            DatagramPacket resposta = new DatagramPacket(buffer, buffer.length);
            socket.setSoTimeout(1000);
            socket.receive(resposta);

            String ack = new String(resposta.getData(), 0, resposta.getLength()).trim();
            return ack.equalsIgnoreCase("ACK");

        } catch (Exception e) {
            System.err.println("[UDP] Erro ao enviar os dados ou receber oACK: " +e.getMessage());
            return false;
        }
    }

    public void fechar() {
    }
}
