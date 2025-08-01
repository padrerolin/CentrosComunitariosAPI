package com.api.centroscomunitarios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa o estado de ocupação de um centro comunitário.
 * Este modelo contém informações sobre a capacidade total,
 * o número de pessoas atualmente ocupando o centro e o percentual de ocupação.
 *
 * Pontos a considerar para futuras melhorias:
 * - Validar que 'ocupados' não excede 'capacidadeTotal' em nível de serviço.
 * - O campo 'percentual' é derivado dos demais, mas armazená-lo pode
 *   melhorar performance em leituras frequentes, apesar de requerer sincronização.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ocupacao {

    /**
     * Capacidade total do centro comunitário, ou seja, o número máximo de pessoas que o centro suporta.
     */
    private int capacidadeTotal;

    /**
     * Número atual de pessoas ocupando o centro.
     */
    private int ocupados;

    /**
     * Percentual de ocupação calculado a partir de (ocupados / capacidadeTotal) * 100.
     * Inicializado em zero para evitar NPE e refletir o estado inicial.
     */
    private Integer percentual = 0;
}
