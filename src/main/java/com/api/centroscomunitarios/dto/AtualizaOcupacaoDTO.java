package com.api.centroscomunitarios.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class AtualizaOcupacaoDTO {
    @NotNull
    @Min(0)
    private Integer ocupados;
}
