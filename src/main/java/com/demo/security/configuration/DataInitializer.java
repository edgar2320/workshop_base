package com.demo.security.configuration;

import com.demo.security.presentation.dto.request.PermissionCreateDTO;
import com.demo.security.presentation.dto.request.RolCreateDTO;
import com.demo.security.presentation.dto.request.UserCreateDTO;
import com.demo.security.presentation.dto.response.PermissionDTO;
import com.demo.security.presentation.dto.response.RolDTO;
import com.demo.security.service.interfaces.PermissionService;
import com.demo.security.service.interfaces.RolService;
import com.demo.security.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final RolService rolService;
    private final PermissionService permissionService;



    @Override
    public void run(String... args) {
        if (permissionService.count() == 0) {
            inicializarPermisos();
        }
        if (rolService.count() == 0) {
            inicializarRoles();
        }
        if (userService.count() == 0) {
            createUserAdmin();
        }
    }

    private void createUserAdmin() {
        Set<Long> rolesAdmin = Set.of(
                rolService.findByName("ADMIN")
                        .map(RolDTO::getId)
                        .orElseThrow()
        );

        UserCreateDTO admin = new UserCreateDTO(
                "admin",
                "admin123", // Cambiar en producción
                "admin@sistema.com",
                rolesAdmin
        );
        userService.createUser(admin);
    }

    private void inicializarPermisos() {
        // Implementar inicialización de permisos
        List<PermissionCreateDTO> permisosBase = List.of(
                // Permisos para Usuarios
                new PermissionCreateDTO("USER_CREATE", "Crear usuarios"),
                new PermissionCreateDTO("USER_READ", "Ver usuarios"),
                new PermissionCreateDTO("USER_UPDATE", "Actualizar usuarios"),
                new PermissionCreateDTO("USER_DELETE", "Eliminar usuarios"),

                // Permisos para Roles
                new PermissionCreateDTO("ROLE_CREATE", "Crear roles"),
                new PermissionCreateDTO("ROLE_READ", "Ver roles"),
                new PermissionCreateDTO("ROLE_UPDATE", "Actualizar roles"),
                new PermissionCreateDTO("ROLE_DELETE", "Eliminar roles"),

                // Permisos para Permisos
                new PermissionCreateDTO("PERMISSION_CREATE", "Crear permisos"),
                new PermissionCreateDTO("PERMISSION_READ", "Ver permisos"),
                new PermissionCreateDTO("PERMISSION_UPDATE", "Actualizar permisos"),
                new PermissionCreateDTO("PERMISSION_DELETE", "Eliminar permisos")
        );

        permisosBase.forEach(permissionService::createPermission);
    }

    private void inicializarRoles() {
        // Implementar inicialización de roles
        List<PermissionDTO> todosLosPermisos = permissionService.listPermission();
        Set<Long> idsTodosLosPermisos = todosLosPermisos.stream()
                .map(PermissionDTO::getId)
                .collect(Collectors.toSet());

        // Crear roles base
        List<RolCreateDTO> rolesBase = List.of(
                new RolCreateDTO(
                        "ADMIN",
                        "Administrador con acceso total",
                        idsTodosLosPermisos
                ),
                new RolCreateDTO(
                        "USER",
                        "Usuario con acceso básico",
                        Set.of() // Sin permisos iniciales o permisos básicos específicos
                )
        );
        rolesBase.forEach(rolService::createRol);

    }



}