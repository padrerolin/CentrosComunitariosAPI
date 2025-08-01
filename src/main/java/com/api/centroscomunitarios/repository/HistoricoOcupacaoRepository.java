package com.api.centroscomunitarios.repository;

import com.api.centroscomunitarios.model.HistoricoOcupacao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricoOcupacaoRepository extends MongoRepository<HistoricoOcupacao, String> {
}
