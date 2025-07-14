package com.exemplo.produtor;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

public class StatusRepository {
    private final Nitrite db;
    private final ObjectRepository<StatusEnvio> repositorio;

    public StatusRepository() {
        this.db = Nitrite.builder()
                .filePath("produtor.db")
                .openOrCreate();

        this.repositorio = db.getRepository(StatusEnvio.class);
    }

    public int obterUltimaLinhaEnviada(String nomeArquivo) {
        StatusEnvio status = repositorio.find(ObjectFilters.eq("nomeArquivo", nomeArquivo)).firstOrDefault();

        return (status != null) ? status.getUltimaLinhaEnviada() : 0;
    }

    public void salvarStatus(String nomeArquivo, int linha) {
        StatusEnvio status = new StatusEnvio(nomeArquivo, linha);
        repositorio.update(status, true);
        System.out.println("[STATUS] Registro salvo no banco");
        System.out.println("--------------------------------");
    }

    public void fechar() {
        if (db != null) {
            db.close();
        }
    }
}
