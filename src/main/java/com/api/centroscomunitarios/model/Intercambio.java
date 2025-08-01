package com.api.centroscomunitarios.model;

import com.api.centroscomunitarios.dto.ItemNegociadoDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa um intercâmbio de recursos entre dois centros comunitários.
 * Armazenado na coleção "intercambios" do MongoDB.
 *
 * Considerações futuras:
 * - Poderíamos transformar `origemId` e `destinoId` em referências diretas (DBRef) para facilitar joins (embora DBRef não seja recomendado para alta performance no Mongo).
 * - Avaliar se `tipoRecurso`, `descricao` e `quantidade` ainda são necessários, considerando que `recursosEnviados` e `recursosRecebidos` os substituem com mais flexibilidade.
 */
@Data
@Document(collection = "intercambios")
public class Intercambio {

    @Id
    private String id;

    /**
     * ID do centro comunitário que enviou os recursos.
     */
    private String origemId;

    /**
     * ID do centro comunitário que recebeu os recursos.
     */
    private String destinoId;

    /**
     * Tipo de recurso principal negociado (campo legado, uso em desuso).
     * Pode ser mantido por compatibilidade ou ser descontinuado.
     */
    private String tipoRecurso;

    /**
     * Descrição textual do intercâmbio. Pode ser usada para auditoria ou comentários.
     */
    private String descricao;

    /**
     * Quantidade total de recursos (geral ou do tipo principal). Também um campo legado.
     */
    private int quantidade;

    /**
     * Lista de recursos enviados pelo centro de origem.
     */
    private List<ItemNegociadoDTO> recursosEnviados;

    /**
     * Lista de recursos recebidos do centro de destino.
     */
    private List<ItemNegociadoDTO> recursosRecebidos;

    /**
     * Momento em que o intercâmbio foi solicitado.
     */
    private LocalDateTime dataSolicitacao;

    /**
     * Momento em que o intercâmbio foi concluído (pode ser null se estiver pendente).
     */
    private LocalDateTime dataConclusao;

    /**
     * Status atual do intercâmbio
     * Ideal manter os valores possíveis como constantes ou Enum.
     */
    private String status;
}
