package com.api.centroscomunitarios.controller;

import com.api.centroscomunitarios.dto.AtualizaOcupacaoDTO;
import com.api.centroscomunitarios.model.CentroComunitario;
import com.api.centroscomunitarios.service.CentroComunitarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/centros")
public class CentroComunitarioController {

    private final CentroComunitarioService service;

    // Injeção de dependência via construtor para facilitar testes e desacoplamento
    public CentroComunitarioController(CentroComunitarioService service) {
        this.service = service;
    }

    /**
     * Listar todos os centros comunitários cadastrados.
     *
     * OBS: Dependendo do volume de dados, considerar implementar paginação para otimizar resposta.
     */
    @GetMapping
    public ResponseEntity<List<CentroComunitario>> listar() {
        List<CentroComunitario> todos = service.listarTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * Cria um novo centro comunitário.
     *
     * Dúvida: Deveria validar os campos do objeto recebido aqui no controller
     * ou delegar essa responsabilidade para o service?
     */
    @PostMapping
    public ResponseEntity<CentroComunitario> criar(@RequestBody CentroComunitario centro) {
        CentroComunitario salvo = service.salvar(centro);
        return ResponseEntity.ok(salvo);
    }

    /**
     * Busca um centro por seu ID.
     *
     * Retorna 404 caso o centro não exista.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CentroComunitario> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um centro pelo ID.
     *
     * Dúvida: Deveria verificar vínculos (ex: negociações, recursos) antes de excluir?
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza a ocupação de um centro comunitário.
     *
     * Usa validação de DTO via @Valid para garantir integridade dos dados recebidos.
     */
    @PatchMapping("/{id}/ocupacao")
    public ResponseEntity<CentroComunitario> atualizarOcupacao(
            @PathVariable String id,
            @RequestBody @Valid AtualizaOcupacaoDTO dto) {

        CentroComunitario atualizado = service.atualizarOcupacao(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Relatório: lista centros com ocupação maior que 90%.
     */
    @GetMapping("/relatorios/alta-ocupacao")
    public ResponseEntity<List<CentroComunitario>> altaOcupacao() {
        return ResponseEntity.ok(service.buscarComAltaOcupacao());
    }

    /**
     * Relatório: calcula a média de cada tipo de recurso entre todos os centros.
     *
     * Dúvida: Para sistemas com grande volume, seria interessante delegar para um
     * serviço assíncrono ou banco com agregações específicas?
     */
    @GetMapping("/relatorios/media-recursos")
    public ResponseEntity<Map<String, Double>> mediaRecursos() {
        Map<String, Double> medias = service.calcularMediaRecursos();
        return ResponseEntity.ok(medias);
    }
}
