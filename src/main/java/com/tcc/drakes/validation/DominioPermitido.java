package com.tcc.drakes.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DominioPermitidoValidator.class)
public @interface DominioPermitido {

    String message() default "O domínio do e-mail não é permitido.";

    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}