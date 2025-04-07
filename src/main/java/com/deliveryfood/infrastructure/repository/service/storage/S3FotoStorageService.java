package com.deliveryfood.infrastructure.repository.service.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.deliveryfood.domain.service.FotoStorageService;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recuperar'");
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'armazenar'");
    }

    @Override
    public void remover(String nomeArquivo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }

}
