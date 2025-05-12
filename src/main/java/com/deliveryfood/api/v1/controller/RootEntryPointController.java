package com.deliveryfood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.core.security.DeliverySecurity;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity;

    @GetMapping
    @Operation(hidden = true)
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if (deliverySecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(deliveryLinks.linkToCozinhas("cozinhas"));
        }

        if (deliverySecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(deliveryLinks.linkToPedidos("pedidos"));
        }

        if (deliverySecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(deliveryLinks.linkToRestaurantes("restaurantes"));
        }

        if (deliverySecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(deliveryLinks.linkToGrupos("grupos"));
            rootEntryPointModel.add(deliveryLinks.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(deliveryLinks.linkToPermissoes("permissoes"));
        }

        if (deliverySecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(deliveryLinks.linkToFormasPagamento("formas-pagamento"));
        }

        if (deliverySecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(deliveryLinks.linkToEstados("estados"));
        }

        if (deliverySecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(deliveryLinks.linkToCidades("cidades"));
        }

        if (deliverySecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(deliveryLinks.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

    }
}
