package com.deliveryfood.api.v2.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputV2 {
    
    @NotBlank
    private String nomeCozinha;
}
