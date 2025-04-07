package com.deliveryfood.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("deliveryfood.storage")
public class StorageProperties {

    private Local local = new Local();

    @Getter
    @Setter
    public class Local {

        private Path diretoriosFotos;

    }

    @Getter
    @Setter
    public class S3{
        
        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String bucket;
        private String regiao;
        private String diretoriosFotos;
        
    }
}
