package com.api.centroscomunitarios.model;

public enum ValorRecurso {
    MEDICO("Médico", 4),
    VOLUNTARIO("Voluntário", 3),
    KIT_MEDICO("Kit de suprimentos médicos", 7),
    VEICULO("Veículo de transporte", 5),
    CESTA("Cesta básica", 2);

    private final String nome;
    private final int pontos;

    ValorRecurso(String nome, int pontos) {
        this.nome = nome;
        this.pontos = pontos;
    }

    public static int pontosPara(String nomeRecurso) {
        for (ValorRecurso v : values()) {
            if (v.nome.equalsIgnoreCase(nomeRecurso)) {
                return v.pontos;
            }
        }
        throw new IllegalArgumentException("Recurso desconhecido: " + nomeRecurso);
    }
}
