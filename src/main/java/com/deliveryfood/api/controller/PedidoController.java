package com.deliveryfood.api.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.assembler.PedidoInputDisassembler;
import com.deliveryfood.api.assembler.PedidoModelAssembler;
import com.deliveryfood.api.assembler.PedidoResumoModelAssembler;
import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.api.model.PedidoResumoModel;
import com.deliveryfood.api.model.input.PedidoInput;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.PedidoRepository;
import com.deliveryfood.domain.service.EmissaoPedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

        if (StringUtils.isNotBlank(campos)) {
            // filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
            //     campos.split(",")));

            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                Arrays.stream(campos.split(","))
                        .map(String::trim)  // Remove espaços em branco antes e depois de cada campo
                        .toArray(String[]::new)));
        }

        pedidosWrapper.setFilters(filterProvider);

        return pedidosWrapper;
    }

    // @GetMapping
    // public List<PedidoResumoModel> listar() {
    //     List<Pedido> todosPedidos = pedidoRepository.findAll();

    //     return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
    // }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        
        return pedidoModelAssembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
    
            // TODO pegar usuario autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);
    
            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
