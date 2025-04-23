package com.deliveryfood.api.v1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.v1.assembler.PedidoInputDisassembler;
import com.deliveryfood.api.v1.assembler.PedidoModelAssembler;
import com.deliveryfood.api.v1.assembler.PedidoResumoModelAssembler;
import com.deliveryfood.api.v1.model.PedidoModel;
import com.deliveryfood.api.v1.model.PedidoResumoModel;
import com.deliveryfood.api.v1.model.input.PedidoInput;
import com.deliveryfood.core.data.PageableTranslator;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.PedidoRepository;
import com.deliveryfood.domain.repository.filter.PedidoFilter;
import com.deliveryfood.domain.service.EmissaoPedidoService;
import com.deliveryfood.infrastructure.repository.spec.PedidoSpecs;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1/pedidos")
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

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(@PageableDefault(size = 10) Pageable pageable, PedidoFilter filtro) {
        Pageable pageableTraduzido = traduzirPageable(pageable);
        // pageable = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageImpl<>(pedidosPage.getContent(), pageable, pedidosPage.getTotalElements());
        // o código acima faz a mesma coisa que o de baixo, porém sem precisar criar o PageWrapper
        // o PageWrapper foi deixado no código só para exemplo
        // pedidosPage = new PageWrapper<>(pedidosPage, pageable);
        
        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

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

    private Pageable traduzirPageable(Pageable apiPageable) {
        // tambem é possível fazer usando o Map.of()
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restaurante.nome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "nomeCliente", "cliente.nome"

        );

        return PageableTranslator.translator(apiPageable, mapeamento);
    }
}
