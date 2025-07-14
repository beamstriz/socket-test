package com.exemplo.receptor;

public class Main {
    public static void main(String[] args) {
        int porta = 5000;

        for (String arg : args) {
            if (arg.startsWith("--porta=")) {
                porta = Integer.parseInt(arg.substring(8));
            }
        }

        System.out.println("Iniciando receptor nas portas TCP/UDP "+porta);
        System.out.println("--------------------------------");

        Thread tcpThread =new Thread(new ServidorTCP(porta));
        Thread udpThread = new Thread(new ServidorUDP(porta));

        tcpThread.start();
        udpThread.start();
    }
}
