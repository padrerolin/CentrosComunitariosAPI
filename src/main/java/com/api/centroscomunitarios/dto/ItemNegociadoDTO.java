package com.api.centroscomunitarios.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemNegociadoDTO {
    @NotNull
    private String nome;       // ex: "Voluntário", "Kit de suprimentos médicos"
    @Min(1)
    private int quantidade;
}
