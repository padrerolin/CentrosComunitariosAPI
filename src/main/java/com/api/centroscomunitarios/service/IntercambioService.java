package com.api.centroscomunitarios.service;

import com.api.centroscomunitarios.dto.NegociacaoRequestDTO;
import com.api.centroscomunitarios.model.Intercambio;

import java.time.LocalDateTime;
import java.util.List;

public interface IntercambioService {
    List<Intercambio> listarTodos();
    Intercambio concluir(String id);
    Intercambio negociar(NegociacaoRequestDTO req);
    List<Intercambio> buscarHistorico(String centroId, LocalDateTime desde);
}
