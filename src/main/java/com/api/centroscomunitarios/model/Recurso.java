package com.api.centroscomunitarios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um recurso disponível em um centro comunitário.
 * Pode ser de natureza pessoal (ex: voluntários) ou material (ex: alimentos, equipamentos).
 *
 * Essa classe é utilizada na composição do CentroComunitario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recurso {

    /**
     * Tipo do recurso.
     * Pode assumir valores como "pessoal" ou "material".
     * Útil para categorizar os recursos durante o gerenciamento e intercâmbio.
     */
    private String tipo;

    /**
     * Nome do recurso.
     * Exemplo: "Voluntários", "Cestas básicas", "Medicamentos".
     */
    private String nome;

    /**
     * Quantidade disponível do recurso no centro comunitário.
     */
    private int quantidade;
}
