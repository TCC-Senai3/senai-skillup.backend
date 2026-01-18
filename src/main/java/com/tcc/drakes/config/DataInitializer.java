package com.tcc.drakes.config;

import com.tcc.drakes.entities.Permissao;
import com.tcc.drakes.entities.Role;
import com.tcc.drakes.repositories.PermissaoRepository;
import com.tcc.drakes.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Override
    public void run(String... args) throws Exception {
        
        Permissao criarFormularioPermissao = permissaoRepository.findByNome("CRIAR_FORMULARIO")
                .orElseGet(() -> permissaoRepository.save(new Permissao("CRIAR_FORMULARIO")));


        roleRepository.findByNome("ROLE_ADMIN").orElseGet(() -> {
            Role adminRole = new Role("ROLE_ADMIN");
            adminRole.setPermissoes(new HashSet<>(Arrays.asList(criarFormularioPermissao)));
            return roleRepository.save(adminRole);
        });


        roleRepository.findByNome("ROLE_USER").orElseGet(() ->
            roleRepository.save(new Role("ROLE_USER"))
        );

        
        roleRepository.findByNome("ROLE_CRIADOR_FORMULARIO").orElseGet(() -> {
            Role criadorRole = new Role("ROLE_CRIADOR_FORMULARIO");
            criadorRole.setPermissoes(new HashSet<>(Arrays.asList(criarFormularioPermissao)));
            return roleRepository.save(criadorRole);
        });
    }
}