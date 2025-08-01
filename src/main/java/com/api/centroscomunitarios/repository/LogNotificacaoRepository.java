package com.api.centroscomunitarios.repository;

import com.api.centroscomunitarios.model.LogNotificacao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogNotificacaoRepository extends MongoRepository<LogNotificacao, String> {
}
