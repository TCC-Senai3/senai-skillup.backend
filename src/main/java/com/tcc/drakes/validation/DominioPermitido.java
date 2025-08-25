package com.tcc.drakes.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DominioPermitidoValidator.class) // <-- Conecta com a lógica que faremos no Passo 3
public @interface DominioPermitido {

    // Mensagem de erro padrão se a validação falhar
    String message() default "O domínio do e-mail não é permitido.";

    // Linhas padrão obrigatórias para anotações de validação
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}