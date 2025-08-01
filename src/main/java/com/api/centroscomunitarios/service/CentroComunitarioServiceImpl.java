package com.api.centroscomunitarios.service;

import com.api.centroscomunitarios.dto.AtualizaOcupacaoDTO;
import com.api.centroscomunitarios.model.CentroComunitario;
import com.api.centroscomunitarios.model.HistoricoOcupacao;
import com.api.centroscomunitarios.model.LogNotificacao;
import com.api.centroscomunitarios.repository.CentroComunitarioRepository;
import com.api.centroscomunitarios.repository.HistoricoOcupacaoRepository;
import com.api.centroscomunitarios.repository.LogNotificacaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@Service
public class CentroComunitarioServiceImpl implements CentroComunitarioService {

    private final CentroComunitarioRepository repository;
    private final HistoricoOcupacaoRepository historicoRepository;
    private final LogNotificacaoRepository notificacaoRepository;
    public CentroComunitarioServiceImpl(
            CentroComunitarioRepository repository,
            HistoricoOcupacaoRepository historicoRepository,
            LogNotificacaoRepository notificacaoRepository
    ) {
        this.repository = repository;
        this.historicoRepository = historicoRepository;
        this.notificacaoRepository = notificacaoRepository;
    }

    @Override
    public List<CentroComunitario> listarTodos() {
        List<CentroComunitario> centros = repository.findAll();
        centros.forEach(c -> {
            int cap = c.getOcupacao().getCapacidadeTotal();
            int ocu = c.getOcupacao().getOcupados();
            c.getOcupacao().setPercentual(cap == 0 ? 0 : (int)(100.0 * ocu / cap));
        });
        return centros;
    }
    @Override
    public CentroComunitario salvar(CentroComunitario centro){
        return repository.save(centro);
    }

    @Override
    public Optional<CentroComunitario> buscarPorId(String id) {
        return repository.findById(id);
    }

    @Override
    public void deletar(String id) {
        repository.deleteById(id);
    }

    @Override
    public CentroComunitario atualizarOcupacao(String idCentro, AtualizaOcupacaoDTO dto) {
        CentroComunitario centro = repository.findById(idCentro)
                .orElseThrow(() -> new IllegalArgumentException("Centro não encontrado: " + idCentro));

        int capacidade = centro.getOcupacao().getCapacidadeTotal();
        centro.getOcupacao().setOcupados(dto.getOcupados());

        double percentual = 100.0 * dto.getOcupados() / capacidade;
        centro.getOcupacao().setPercentual((int) percentual);

        CentroComunitario salvo = repository.save(centro);

        HistoricoOcupacao historico = new HistoricoOcupacao();
        historico.setCentroId(centro.getId());
        historico.setOcupados(dto.getOcupados());
        historico.setPercentual((int) percentual);
        historico.setDataHora(LocalDateTime.now());
        historicoRepository.save(historico);

        if (percentual >= 100.0) {
            LogNotificacao notificacao = new LogNotificacao();
            notificacao.setCentroId(centro.getId());
            notificacao.setMensagem("Centro " + centro.getId() + " atingiu 100% de ocupação.");
            notificacao.setDataHora(LocalDateTime.now());
            notificacaoRepository.save(notificacao);

            System.out.printf("NOTIFICAÇÃO: Centro %s atingiu 100%% de ocupação.%n", centro.getId());
        }

        return salvo;
    }
    public List<CentroComunitario> buscarComAltaOcupacao() {
        return repository.findAll().stream()
                .filter(c -> {
                    Integer pct = c.getOcupacao().getPercentual();
                    return pct != null && pct > 90;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> calcularMediaRecursos() {
        List<CentroComunitario> todos = repository.findAll();
        int totalCentros = todos.size();

        // Soma as quantidades por recurso
        Map<String, Integer> somaPorRecurso = new HashMap<>();
        todos.forEach(c -> {
            c.getRecursos().forEach(r ->
                    somaPorRecurso.merge(r.getNome(), r.getQuantidade(), Integer::sum)
            );
        });

        // Calcula a média dividindo pelo número de centros
        return somaPorRecurso.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue() / (double) totalCentros
                ));
    }
}
