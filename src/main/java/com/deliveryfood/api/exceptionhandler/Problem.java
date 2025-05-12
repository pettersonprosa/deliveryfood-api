package com.deliveryfood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @Schema(example = "400")
    private Integer status;
    
    @Schema(example = "https://deliveryfood.com.br/dados-invalidos")
    private String type;
    
    @Schema(example = "Dados inválidos")
    private String title;
    
    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String userMessage;
    
    @Schema(example = "2022-07-15T11:21:50.902245498Z")
    private OffsetDateTime timestamp;
    
    @Schema(description = "Lista de objetos ou campos que geraram o erro")
    private List<Object> objects;

    @Schema(name = "ObjetoProblema")
    @Getter
    @Builder
    public static class Object {
        
        @Schema(example = "preco")
        private String name;
        
        @Schema(example = "O preço é inválido")
        private String userMessage;
    }

}
