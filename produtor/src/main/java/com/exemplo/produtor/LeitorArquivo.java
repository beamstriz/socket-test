package com.exemplo.produtor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LeitorArquivo {
    private final Path caminhoArquivo;
    private String tipo;

    public LeitorArquivo(Path caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String obterTipo() {
        if (tipo == null) {
            try {
                List<String> linhas = Files.readAllLines(caminhoArquivo);
                if (linhas.isEmpty()) throw new RuntimeException("Arquivo vazio");
                tipo = linhas.get(0).trim().toLowerCase();

                if (!tipo.equals("ascii") && !tipo.equals("binário") && !tipo.equals("binario")) {
                    throw new IllegalArgumentException("Tipo inválido: "+tipo);
                }

            } catch (IOException e) {
                throw new RuntimeException("Erro ao ler o arquivo: " +e.getMessage());
            }
        }
        return tipo;
    }

    public List<String> lerLinhas() {
        try {
            List<String> todasLinhas = Files.readAllLines(caminhoArquivo);
            List<String> conteudo = new ArrayList<>();

            for (int i = 1; i < todasLinhas.size(); i++) {
                String linha = todasLinhas.get(i).trim();
                if (!linha.isEmpty()) {
                    conteudo.add(linha);
                }
            }
            return conteudo;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o conteudo do arquivo: " + e.getMessage());
        }
    }

    public byte[] hexParaBytes(String hex) {
        int len = hex.length();
        byte[] resultado = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            resultado[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                     + Character.digit(hex.charAt(i+1), 16));
        }
        return resultado;
    }

    public String bytesParaHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

}
