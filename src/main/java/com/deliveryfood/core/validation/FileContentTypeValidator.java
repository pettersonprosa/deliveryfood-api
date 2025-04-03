package com.deliveryfood.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedContentTypes = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || allowedContentTypes.contains(value.getContentType());
    }
}
