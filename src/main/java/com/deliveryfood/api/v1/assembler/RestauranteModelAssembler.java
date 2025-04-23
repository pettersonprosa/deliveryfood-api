package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.RestauranteController;
import com.deliveryfood.api.v1.model.RestauranteModel;
import com.deliveryfood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(deliveryLinks.linkToRestaurantes("restaurantes"));
        restauranteModel.getCozinha().add(deliveryLinks.linkToCozinha(restaurante.getCozinha().getId()));
        restauranteModel.add(deliveryLinks.linkToProdutos(restaurante.getId(), "produtos"));

        // Null-safety para endereço/cidade
        if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
            restauranteModel.getEndereco().getCidade().add(
                    deliveryLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
        }

        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(
                    deliveryLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(deliveryLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(deliveryLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(deliveryLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }

        restauranteModel.add(deliveryLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
        restauranteModel.add(deliveryLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(deliveryLinks.linkToRestaurantes());
    }
}
