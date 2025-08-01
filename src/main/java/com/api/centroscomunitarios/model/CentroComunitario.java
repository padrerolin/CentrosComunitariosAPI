package com.api.centroscomunitarios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

/**
 * Representa um Centro Comunitário armazenado na coleção "centros" do MongoDB.
 * Um centro pode conter múltiplos recursos e uma estrutura de ocupação.
 *
 * Pontos de atenção para evolução futura:
 * - Validar consistência entre ocupação e número de recursos disponíveis.
 * - Adicionar campos como "ativo", "dataCriacao", etc., para auditoria.
 */
@Document(collection = "centros")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CentroComunitario {

    @Id
    private String id;

    /**
     * Nome do centro comunitário. Idealmente único por cidade.
     */
    private String nome;

    /**
     * Pode ser futuramente substituído por uma estrutura georreferenciada.
     */
    private String endereco;

    /**
     * Lista de recursos disponíveis neste centro, com suas quantidades.
     */
    private List<Recurso> recursos;

    /**
     * Representa o estado de ocupação atual do centro.
     */
    private Ocupacao ocupacao;
}
