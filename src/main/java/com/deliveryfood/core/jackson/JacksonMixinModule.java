package com.deliveryfood.core.jackson;

import org.springframework.stereotype.Component;

import com.deliveryfood.api.model.mixin.CidadeMixin;
import com.deliveryfood.api.model.mixin.CozinhaMixin;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Cozinha;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
