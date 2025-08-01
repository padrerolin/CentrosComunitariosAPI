package com.api.centroscomunitarios.service;

import com.api.centroscomunitarios.dto.NegociacaoRequestDTO;
import com.api.centroscomunitarios.dto.ItemNegociadoDTO;
import com.api.centroscomunitarios.model.CentroComunitario;
import com.api.centroscomunitarios.model.Intercambio;
import com.api.centroscomunitarios.model.ValorRecurso;
import com.api.centroscomunitarios.repository.CentroComunitarioRepository;
import com.api.centroscomunitarios.repository.IntercambioRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IntercambioServiceImpl implements IntercambioService {

    private final IntercambioRepository intercambioRepo;
    private final CentroComunitarioRepository centroRepo;

    // Construtor com injeção de dependências, seguindo boas práticas do Spring
    public IntercambioServiceImpl(IntercambioRepository intercambioRepo,
                                  CentroComunitarioRepository centroRepo) {
        this.intercambioRepo = intercambioRepo;
        this.centroRepo = centroRepo;
    }

    /**
     * Retorna todos os registros de intercâmbio existentes no sistema.
     * Pode ser útil implementar paginação futura (via Pageable).
     */
    @Override
    public List<Intercambio> listarTodos() {
        return intercambioRepo.findAll();
    }

    /**
     * Marca um intercâmbio como concluído e define a data de conclusão.
     *
     * Valida se o ID existe no banco e persiste a alteração.
     */
    @Override
    public Intercambio concluir(String id) {
        Intercambio i = intercambioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Intercâmbio não encontrado: " + id));

        i.setStatus("concluido");
        i.setDataConclusao(LocalDateTime.now());

        return intercambioRepo.save(i);
    }

    /**
     * Realiza a negociação entre dois centros comunitários.
     * Valida regras de pontos e aplica exceções baseadas na ocupação dos centros (> 90%).
     */
    public Intercambio negociar(NegociacaoRequestDTO req) {
        // Busca os dois centros participantes da troca
        CentroComunitario origem  = centroRepo.findById(req.getOrigemId())
                .orElseThrow(() -> new IllegalArgumentException("Origem não encontrada"));
        CentroComunitario destino = centroRepo.findById(req.getDestinoId())
                .orElseThrow(() -> new IllegalArgumentException("Destino não encontrado"));

        // Soma os pontos dos recursos para validação de equivalência
        int pontosEnviados  = somaPontos(req.getRecursosEnviados());
        int pontosRecebidos = somaPontos(req.getRecursosRecebidos());

        // Verifica exceção de alta ocupação (centros com >90% podem flexibilizar trocas)
        boolean excecaoOrigem  = origem.getOcupacao().getPercentual()  > 90;
        boolean excecaoDestino = destino.getOcupacao().getPercentual() > 90;

        // Rejeita se pontos forem diferentes e nenhum dos centros estiver em exceção
        if (!(pontosEnviados == pontosRecebidos || excecaoOrigem || excecaoDestino)) {
            throw new IllegalStateException("Pontos diferentes e sem exceção de ocupação (>90%).");
        }

        // Atualiza os recursos nos centros
        ajustarRecursos(origem,  req.getRecursosEnviados(),  -1);
        ajustarRecursos(destino, req.getRecursosRecebidos(), +1);

        // Inverso: destino envia e origem recebe
        ajustarRecursos(destino, req.getRecursosEnviados(),  -1);
        ajustarRecursos(origem,  req.getRecursosRecebidos(), +1);

        // Salva alterações nos centros
        centroRepo.save(origem);
        centroRepo.save(destino);

        // Registra histórico do intercâmbio
        Intercambio i = new Intercambio();
        i.setOrigemId(req.getOrigemId());
        i.setDestinoId(req.getDestinoId());
        i.setRecursosEnviados(req.getRecursosEnviados());
        i.setRecursosRecebidos(req.getRecursosRecebidos());
        i.setDataSolicitacao(LocalDateTime.now());
        i.setStatus("concluido");

        return intercambioRepo.save(i);
    }

    /**
     * Soma os pontos dos itens negociados com base na tabela de valores (ValorRecurso).
     *
     * @param itens Lista de recursos com quantidade
     * @return total de pontos
     */
    private int somaPontos(List<ItemNegociadoDTO> itens) {
        return itens.stream()
                .mapToInt(item ->
                        ValorRecurso.pontosPara(item.getNome()) * item.getQuantidade()
                ).sum();
    }

    /**
     * Atualiza os recursos de um centro com base na troca.
     * Multiplica a quantidade por um fator (1 para adicionar, -1 para remover).
     *
     * @param centro Centro a ser atualizado
     * @param itens  Recursos envolvidos
     * @param factor 1 ou -1
     */
    private void ajustarRecursos(CentroComunitario centro,
                                 List<ItemNegociadoDTO> itens,
                                 int factor) {
        itens.forEach(item -> {
            centro.getRecursos().stream()
                    .filter(r -> r.getNome().equalsIgnoreCase(item.getNome()))
                    .findFirst()
                    .ifPresent(r -> r.setQuantidade(r.getQuantidade() + factor * item.getQuantidade()));
        });
    }

    /**
     * Busca o histórico de intercâmbios (como origem ou destino) realizados por um centro,
     * dentro de um intervalo de tempo.
     */
    public List<Intercambio> buscarHistorico(String centroId, LocalDateTime desde) {
        LocalDateTime agora = LocalDateTime.now();
        return intercambioRepo
                .findByOrigemIdOrDestinoIdAndDataSolicitacaoBetween(
                        centroId, centroId, desde, agora
                );
    }
}

