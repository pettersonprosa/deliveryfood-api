package com.deliveryfood.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.deliveryfood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.deliveryfood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.deliveryfood.api.v1.model.FormaPagamentoModel;
import com.deliveryfood.api.v1.model.input.FormaPagamentoInput;
import com.deliveryfood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.deliveryfood.core.security.CheckSecurity;
import com.deliveryfood.domain.model.FormaPagamento;
import com.deliveryfood.domain.repository.FormaPagamentoRepository;
import com.deliveryfood.domain.service.CadastroFormaPagamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @CheckSecurity.FormasPagamento.PodeConsultar
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond()); 
        }

        // À partir daqui já é possível saber se o processamento de listagem deverá ser feito ou não

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamento> todasFormasPagamento = formaPagamentoRepository.findAll();

        CollectionModel<FormaPagamentoModel> formasPagamentosModel = formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamento);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formasPagamentosModel);
    }

    @CheckSecurity.FormasPagamento.PodeConsultar
    @Override
    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        
        String eTag = "0";

        OffsetDateTime dataAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacaoById(formaPagamentoId);

        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }
        
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);
        
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoModel);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainModel(formaPagamentoInput);

        formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @Override
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);

        return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @Override
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamento.excluir(formaPagamentoId);
        
        return ResponseEntity.noContent().build();
    }

}
