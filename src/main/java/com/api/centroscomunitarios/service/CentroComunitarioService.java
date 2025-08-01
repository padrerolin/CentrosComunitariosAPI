package com.api.centroscomunitarios.service;

import com.api.centroscomunitarios.dto.AtualizaOcupacaoDTO;
import com.api.centroscomunitarios.model.CentroComunitario;

import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface CentroComunitarioService {
    List<CentroComunitario> listarTodos();
    CentroComunitario salvar(CentroComunitario centro);
    Optional<CentroComunitario> buscarPorId(String id);
    void deletar(String id);
    CentroComunitario atualizarOcupacao(String id, AtualizaOcupacaoDTO dto);
    List<CentroComunitario> buscarComAltaOcupacao();
    Map<String, Double> calcularMediaRecursos();
}
