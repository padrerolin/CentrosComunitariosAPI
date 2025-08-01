package com.api.centroscomunitarios.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class NegociacaoRequestDTO {
    @NotNull
    private String origemId;

    @NotNull
    private String destinoId;

    @NotEmpty
    private List<ItemNegociadoDTO> recursosEnviados;

    @NotEmpty
    private List<ItemNegociadoDTO> recursosRecebidos;
}
