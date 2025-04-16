package com.deliveryfood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.ResourceUriHelper;
import com.deliveryfood.api.assembler.CidadeInputDisassembler;
import com.deliveryfood.api.assembler.CidadeModelAssembler;
import com.deliveryfood.api.model.CidadeModel;
import com.deliveryfood.api.model.input.CidadeInput;
import com.deliveryfood.domain.exception.EstadoNaoEncontradoException;
import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.repository.CidadeRepository;
import com.deliveryfood.domain.service.CadastroCidadeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public CollectionModel<CidadeModel> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();

        List<CidadeModel> cidadesModel = cidadeModelAssembler.toCollectionModel(todasCidades);

        CollectionModel<CidadeModel> cidadesCollectionModel = CollectionModel.of(cidadesModel);

        cidadesModel.forEach(cidadeModel -> {
            cidadeModel.add(linkTo(methodOn(CidadeController.class)
                    .buscar(cidadeModel.getId())).withSelfRel());

            cidadeModel.add(linkTo(methodOn(CidadeController.class)
                    .listar()).withRel("cidades"));

            cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
                    .buscar(cidadeModel.getEstado().getId())).withSelfRel());
        });

        cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());

        return cidadesCollectionModel;
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

        cidadeModel.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeModel.getId())).withSelfRel());

        cidadeModel.add(linkTo(methodOn(CidadeController.class)
                .listar()).withRel("cidades"));

        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId())).withSelfRel());

        return cidadeModel;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            cidade = cadastroCidade.salvar(cidade);

            CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidade.salvar(cidadeAtual);

            return cidadeModelAssembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }

}
