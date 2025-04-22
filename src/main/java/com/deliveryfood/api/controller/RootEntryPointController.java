package com.deliveryfood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.DeliveryLinks;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private DeliveryLinks deliveryLinks;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(deliveryLinks.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(deliveryLinks.linkToPedidos("pedidos"));
        rootEntryPointModel.add(deliveryLinks.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(deliveryLinks.linkToGrupos("grupos"));
        rootEntryPointModel.add(deliveryLinks.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(deliveryLinks.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(deliveryLinks.linkToFormasPagamento("formas-pagamento"));
        rootEntryPointModel.add(deliveryLinks.linkToEstados("estados"));
        rootEntryPointModel.add(deliveryLinks.linkToCidades("cidades"));
        rootEntryPointModel.add(deliveryLinks.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

    }
}
