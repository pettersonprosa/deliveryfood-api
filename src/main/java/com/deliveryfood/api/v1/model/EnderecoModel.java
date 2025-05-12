package com.deliveryfood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel extends RepresentationModel<EnderecoModel> {

    @Schema(example = "38400-000")
    private String cep;

    @Schema(example = "Rua Floriano Peixoto")
    private String logradouro;
    
    @Schema(example = "\"1500\"")
    private String numero;
    
    @Schema(example = "Apto 901")
    private String complemento;
    
    @Schema(example = "Centro")
    private String bairro;
    
    private CidadeResumoModel cidade;

}
