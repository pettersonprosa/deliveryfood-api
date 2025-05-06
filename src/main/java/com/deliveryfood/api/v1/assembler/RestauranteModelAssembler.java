package com.deliveryfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.RestauranteController;
import com.deliveryfood.api.v1.model.RestauranteModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        if (deliverySecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(deliveryLinks.linkToRestaurantes("restaurantes"));
        }

        if (deliverySecurity.podeGerenciarCadastroRestaurantes()) {
            if (restaurante.ativacaoPermitida()) {
                restauranteModel.add(
                        deliveryLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }
    
            if (restaurante.inativacaoPermitida()) {
                restauranteModel.add(deliveryLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }

        if (deliverySecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida()) {
                restauranteModel.add(deliveryLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }
    
            if (restaurante.fechamentoPermitido()) {
                restauranteModel.add(deliveryLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }

        if (deliverySecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(deliveryLinks.linkToProdutos(restaurante.getId(), "produtos"));
        }

        if (deliverySecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(deliveryLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }

        if (deliverySecurity.podeConsultarCidades()) {
            // Null-safety para endereço/cidade
            if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
                restauranteModel.getEndereco().getCidade().add(
                        deliveryLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
            }

        }

        if (deliverySecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(deliveryLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
        }

        if (deliverySecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteModel.add(deliveryLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);
    
        if (deliverySecurity.podeConsultarRestaurantes()) {
            collectionModel.add(deliveryLinks.linkToRestaurantes());
        }
        
        return collectionModel;
    }
}
