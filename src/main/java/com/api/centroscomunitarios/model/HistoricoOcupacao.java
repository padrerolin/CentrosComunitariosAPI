package com.api.centroscomunitarios.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Representa um registro histórico da ocupação de um centro comunitário.
 * Armazenado na coleção "historico_ocupacao" do MongoDB.
 *
 * Possíveis evoluções:
 * - Incluir status ou motivo para mudança de ocupação.
 * - Armazenar ID do usuário ou sistema que gerou o registro (auditoria).
 */
@Data
@Document(collection = "historico_ocupacao")
public class HistoricoOcupacao {

    @Id
    private String id;

    /**
     * Referência ao centro comunitário monitorado.
     * Idealmente, este campo deveria ser indexado para melhorar buscas.
     */
    private String centroId;

    /**
     * Quantidade absoluta de pessoas/recursos ocupando o centro no momento.
     */
    private int ocupados;

    /**
     * Percentual de ocupação em relação à capacidade total do centro.
     * A capacidade total pode estar em outra tabela ou ser padronizada.
     */
    private int percentual;

    /**
     * Data e hora em que o dado foi coletado ou computado.
     */
    private LocalDateTime dataHora;
}
