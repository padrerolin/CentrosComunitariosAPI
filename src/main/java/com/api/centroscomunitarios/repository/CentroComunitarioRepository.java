package com.api.centroscomunitarios.repository;

import com.api.centroscomunitarios.model.CentroComunitario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroComunitarioRepository extends MongoRepository<CentroComunitario, String> {
}
