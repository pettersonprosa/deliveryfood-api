package com.deliveryfood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.assembler.UsuarioModelAssembler;
import com.deliveryfood.api.v1.model.UsuarioModel;
import com.deliveryfood.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.deliveryfood.core.security.CheckSecurity;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity;

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler
                .toCollectionModel(restaurante.getResponsaveis())
                .removeLinks();
                
                
        usuariosModel.add(deliveryLinks.linkToResponsaveisRestaurante(restauranteId));

        if (deliverySecurity.podeGerenciarCadastroRestaurantes()) {
            usuariosModel.add(deliveryLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

            usuariosModel.getContent().forEach(usuarioModel -> {
                usuarioModel.add(deliveryLinks.linkToRestauranteResponsavelDesassociacao(
                        restauranteId, usuarioModel.getId(), "desassociar"));
            });
        }
    
        return usuariosModel;
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

}
