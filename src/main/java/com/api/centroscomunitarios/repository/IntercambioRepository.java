package com.api.centroscomunitarios.repository;

import com.api.centroscomunitarios.model.Intercambio;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface IntercambioRepository extends MongoRepository<Intercambio, String> {
    List<Intercambio> findByOrigemIdOrDestinoIdAndDataSolicitacaoBetween(
            String origemId,
            String destinoId,
            LocalDateTime desde,
            LocalDateTime ate
    );
}
