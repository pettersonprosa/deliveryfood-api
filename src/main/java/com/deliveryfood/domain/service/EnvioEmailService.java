package com.deliveryfood.domain.service;

import java.util.Set;

import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    class Mensagem {

        @Singular
        private Set<String> destinatarios;
        
        @NonNull
        private String assunto;

        @NonNull
        private String corpo;

    }
}
