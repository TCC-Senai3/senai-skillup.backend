package com.tcc.drakes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class DominioPermitidoValidator implements ConstraintValidator<DominioPermitido, String> {

    // Esta anotação injeta o valor da nossa propriedade do application.properties
    // na variável 'allowedDomainsString'.
    @Value("${validation.allowed-domains}")
    private String allowedDomainsString;

    private List<String> dominiosPermitidos;

    
    @Override
    public void initialize(DominioPermitido constraintAnnotation) {
        this.dominiosPermitidos = Arrays.stream(allowedDomainsString.split(","))
                                        .map(String::trim) 
                                        .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true;
        }

        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false; 
        }

        String dominio = parts[1].toLowerCase();

        return dominiosPermitidos.contains(dominio);
    }
}