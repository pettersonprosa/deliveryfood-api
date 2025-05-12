package com.deliveryfood.api.v1.model.input;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.deliveryfood.core.validation.FileContentType;
import com.deliveryfood.core.validation.FileSize;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

    @Schema(description = "Arquivo da foto do produto (máximo 2MB, apenas JPG e PNG)")
    @NotNull
    @FileSize(max = "650KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile arquivo;
    
    @Schema(description = "Descrição da foto do produto")
    @NotBlank
    private String descricao;
}
