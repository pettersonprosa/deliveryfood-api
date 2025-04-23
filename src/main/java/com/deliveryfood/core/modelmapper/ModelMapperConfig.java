package com.deliveryfood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deliveryfood.api.v1.model.EnderecoModel;
import com.deliveryfood.api.v1.model.input.ItemPedidoInput;
import com.deliveryfood.api.v2.model.input.CidadeInputV2;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Endereco;
import com.deliveryfood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class) // TODO ajustar
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoModel.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
        // enderecoModelDest -> Objeto do tipo EnderecoModel && value -> retorno da
        // primeira expressão lambda (enderecoSrc.getCidade().getEstado().getNome())

        return modelMapper;
    }
}
