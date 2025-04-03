package com.deliveryfood.api.model.input;

import org.springframework.web.multipart.MultipartFile;

import com.deliveryfood.core.validation.FileSize;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

    @NotNull
    @FileSize(max = "650KB")
    private MultipartFile arquivo;
    
    @NotBlank
    private String descricao;
}
