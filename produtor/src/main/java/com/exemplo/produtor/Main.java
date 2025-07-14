package com.exemplo.produtor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int porta = 5000;
        String caminhoArquivoStr = null;

        for (String arg : args) {
            if (arg.startsWith("--host=")) {
                host = arg.substring("--host=".length());
            } else if (arg.startsWith("--porta=")) {
                porta = Integer.parseInt(arg.substring("--porta=".length()));
            } else if (arg.startsWith("--arquivo=")) {
                caminhoArquivoStr = arg.substring("--arquivo=".length());
            }
        }

        if (caminhoArquivoStr == null) {
            System.err.println("Informe o caminho do arquivo com .txt");
            return;
        }

        Path caminhoArquivo = Paths.get(caminhoArquivoStr);
        String nomeArquivo = caminhoArquivo.getFileName().toString();

        LeitorArquivo leitor = new LeitorArquivo(caminhoArquivo);
        String tipo = leitor.obterTipo();
        List<String> linhas = leitor.lerLinhas();

        ClienteTCP tcp = new ClienteTCP(host, porta);
        ClienteUDP udp = new ClienteUDP(host, porta);
        StatusRepository repositorio = new StatusRepository();

        System.out.println("Enviando mensagens via " + tipo.toUpperCase());
        System.out.println("--------------------------------");

        int linhaSalvaNoArquivo = repositorio.obterUltimaLinhaEnviada(nomeArquivo);
        int linhaInicial = Math.max(0, linhaSalvaNoArquivo - 1);

        try {
            for (int i = linhaInicial; i < linhas.size(); i++) {
                String linha = linhas.get(i).trim();
                if (linha.isEmpty()) continue;

                byte[] dados;
                if (tipo.equals("ascii")) {
                    dados = linha.getBytes();

                    System.out.println("[TCP] Enviado: " + linha);
                    tcp.enviar(dados);

                    String hex = leitor.bytesParaHex(dados);
                    System.out.print("[UDP] Enviado: " + hex);
                    if (udp.enviar(dados)) {
                        System.out.print(" / ACK recebido");
                    }
                    System.out.println();

                } else {
                    dados = leitor.hexParaBytes(linha);
                    System.out.println("[TCP] Enviado: " + linha);
                    tcp.enviar(dados);

                    System.out.print("[UDP] Enviado: " + linha);
                    if (udp.enviar(dados)) {
                        System.out.print(" / ACK recebido");
                    }
                    System.out.println();
                }

                int linhaNoArquivoReal = i + 2;
                repositorio.salvarStatus(nomeArquivo, linhaNoArquivoReal);
            }

        } catch (Exception e) {
            System.err.println("Erro durante o envio: " + e.getMessage());

        } finally {
            repositorio.fechar();
            tcp.fechar();
            udp.fechar();
        }
    }
}
