package com.deliveryfood.core.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("deliveryfood.email")
public class EmailProperties {

    // Atribuir FAKE como padrão 
    // Isso evita o problema de enviar e-mails de verdade caso você esqueça de definir a propriedade
	private Implementacao impl = Implementacao.FAKE;

    @NotNull
    private String remetente;

    public enum Implementacao {
        SMTP, FAKE
    }

}
