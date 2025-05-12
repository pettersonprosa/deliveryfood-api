package com.deliveryfood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

    @Schema(example = "1")
    @NotNull
    private Long produtoId;

    @Schema(example = "2")
    @Min(1)
    @NotNull
    private Integer quantidade;

    @Schema(example = "Menos picante, por favor")
    private String observacao;
}
