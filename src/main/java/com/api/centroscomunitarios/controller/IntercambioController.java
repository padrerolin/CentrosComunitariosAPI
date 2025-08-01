package com.api.centroscomunitarios.controller;

import com.api.centroscomunitarios.dto.NegociacaoRequestDTO;
import com.api.centroscomunitarios.model.Intercambio;
import com.api.centroscomunitarios.service.IntercambioService;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/intercambios")
public class IntercambioController {

    private final IntercambioService service;

    // Injeção via construtor para facilitar testes e manter o código desacoplado
    public IntercambioController(IntercambioService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros de intercâmbio realizados no sistema.
     *
     * Sugestão futura: adicionar filtros por data ou centro para evitar retorno excessivo de dados.
     */
    @GetMapping
    public ResponseEntity<List<Intercambio>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /**
     * Marca um intercâmbio como concluído.
     *
     * Possivelmente útil validar se o intercâmbio já está finalizado para evitar ações repetidas.
     */
    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Intercambio> concluir(@PathVariable String id) {
        return ResponseEntity.ok(service.concluir(id));
    }

    /**
     * Realiza uma nova negociação entre centros comunitários.
     *
     * Validação do DTO garante integridade da negociação.
     * Dúvida: Poderia incluir regras de negócio no controller, como checar recursos disponíveis?
     */
    @PostMapping("/negociar")
    public ResponseEntity<Intercambio> negociar(
            @RequestBody @Valid NegociacaoRequestDTO dto) {
        Intercambio i = service.negociar(dto);
        return ResponseEntity.ok(i);
    }

    /**
     * Retorna histórico de trocas de um centro a partir de uma data.
     *
     * Data formatada no padrão ISO para compatibilidade com clientes REST (ex: Postman, frontend).
     *
     * OBS: Ideal validar se o centro existe antes de executar a query.
     */
    @GetMapping("/relatorios/historico")
    public ResponseEntity<List<Intercambio>> historico(
            @RequestParam String centroId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime desde
    ) {
        List<Intercambio> lista = service.buscarHistorico(centroId, desde);
        return ResponseEntity.ok(lista);
    }
}

