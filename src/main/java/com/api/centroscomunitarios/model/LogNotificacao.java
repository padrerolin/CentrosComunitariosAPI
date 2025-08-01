package com.api.centroscomunitarios.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Representa o registro de uma notificação enviada a um centro comunitário.
 * Persistido na coleção "notificacoes" do MongoDB.
 *
 * Possíveis melhorias futuras:
 * - Incluir um campo `tipo` para categorizar a notificação (ex: ALERTA, INFO, ERRO).
 * - Armazenar um campo `lido` (boolean) caso o sistema evolua para leitura de notificações no frontend.
 */
@Data
@Document(collection = "notificacoes")
public class LogNotificacao {

    @Id
    private String id;

    /**
     * ID do centro comunitário que recebeu a notificação.
     */
    private String centroId;

    /**
     * Conteúdo textual da notificação.
     */
    private String mensagem;

    /**
     * Data e hora em que a notificação foi registrada ou enviada.
     */
    private LocalDateTime dataHora;
}
