package com.deliveryfood.infrastructure.repository.service.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.core.storage.StorageProperties;
import com.deliveryfood.domain.service.FotoStorageService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recuperar'");
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

            // Criar o PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(storageProperties.getS3().getBucket())
                    .key(caminhoArquivo)
                    .contentType(novaFoto.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            // Enviar o arquivo para o S3
            // Converter InputStream para RequestBody com o tamanho do conteúdo
            RequestBody requestBody = RequestBody.fromInputStream(
                    novaFoto.getInputStream(),
                    novaFoto.getTamanho() // tamanho do arquivo
            );

            s3Client.putObject(putObjectRequest, requestBody);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
        }

    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(storageProperties.getS3().getBucket())
                    .key(getCaminhoArquivo(nomeArquivo))
                    .build();
            
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível deletar arquivo na Amazon S3", e);
        }
    }

}
