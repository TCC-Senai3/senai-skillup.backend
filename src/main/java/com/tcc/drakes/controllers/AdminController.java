package com.tcc.drakes.controllers;

import com.tcc.drakes.dtos.AtualizarRolesDTO;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/usuarios") // Todos os endpoints aqui começarão com /admin/usuarios
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @PutMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Usuario> atualizarRolesDoUsuario(@PathVariable Long id, @RequestBody AtualizarRolesDTO dto) {
        Usuario usuarioAtualizado = usuarioService.atualizarRoles(id, dto.getRoleIds());
        return ResponseEntity.ok(usuarioAtualizado);
    }
}