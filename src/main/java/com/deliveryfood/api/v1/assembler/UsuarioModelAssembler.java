package com.deliveryfood.api.v1.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.api.v1.controller.UsuarioController;
import com.deliveryfood.api.v1.model.UsuarioModel;
import com.deliveryfood.core.security.DeliverySecurity;
import com.deliveryfood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @Autowired
    private DeliverySecurity deliverySecurity; 

    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario usuario) {
        UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);

        modelMapper.map(usuario, usuarioModel);

        if (deliverySecurity.podeConsultarUsuariosGruposPermissoes()) {
            usuarioModel.add(deliveryLinks.linkToUsuarios("usuarios"));
            usuarioModel.add(deliveryLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        }

        return usuarioModel;
    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(linkTo(UsuarioController.class).withSelfRel());
    }

}
