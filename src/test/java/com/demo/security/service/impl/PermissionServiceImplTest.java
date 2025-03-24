package com.demo.security.service.impl;

import com.demo.security.persistence.entities.PermissionEntity;
import com.demo.security.persistence.repository.PermissionRepository;
import com.demo.security.presentation.dto.request.PermissionCreateDTO;
import com.demo.security.presentation.dto.response.PermissionDTO;
import com.demo.security.presentation.exceptions.BadRequestException;
import com.demo.security.util.ConstantApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class) // INTEGRA MOCKITO PARA USAR SUS METHODS
class PermissionServiceImplTest {
    @Mock
    private PermissionRepository permissionRepository;
    @InjectMocks
    private PermissionServiceImpl permissionService;

    @BeforeEach
    void setUp() {
        log.info("Antes de iniciar los test");
    }

    @AfterEach
    void tearDown() {
        log.info("despues de iniciar los test");
    }

    @Test
    void createPermission() {
        log.info("en test de  createPermission");
        //************************************** GIVEN **************************************
        //ESTO ES EL ESCENARIO EN DONDE TENEMOS GUARDAMOS EN BD
        PermissionCreateDTO permission = new PermissionCreateDTO(
                "TEST_READ",
                "TEST READ DESCRIPTION"
        );
        PermissionEntity permissionEntity;
        //SI BUSCAMOS Y NO ESTA
        when(permissionRepository.findByName(anyString())
        ).thenReturn(Optional.empty());
        // MAPEAR DE DTO A ENTIDAD
        permissionEntity= PermissionEntity.builder()
                .name(permission.name())
                .description(permission.description())
                .build();
        //GUARDAR
        when(permissionRepository.save(any(PermissionEntity.class))).thenReturn(permissionEntity);
        //**************************************WHEN  **************************************
        // es donde ejecutamos el metodo y guardamos el resultado
        PermissionDTO result = permissionService.createPermission(permission);
        //**************************************THEN  ***************************************
        // las validaciones minimas pertinenetes
        assertNotNull(result);
        assertEquals("TEST_READ", result.getName());
        verify(permissionRepository).findByName("TEST_READ");
        verify(permissionRepository).save(any(PermissionEntity.class));
    }

    @Test
    void createPermissionException(){
        //************************************** GIVEN **************************************
        //ESTO ES EL ESCENARIO EN DONDE TENEMOS GUARDAMOS EN BD
        PermissionCreateDTO permission = new PermissionCreateDTO(
                "TEST_READ",
                "TEST READ DESCRIPTION"
        );
        // MAPEAR DE DTO A ENTIDAD
        PermissionEntity  permissionEntity= PermissionEntity.builder()
                .name(permission.name())
                .description(permission.description())
                .build();
        //SI BUSCAMOS Y SI ESTA
        when(permissionRepository.findByName(anyString())
        ).thenReturn(Optional.of(permissionEntity));

        //ENCONTRO LANZAR EXCEPTION WHEN Y THEN
        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                permissionService.createPermission(permission)
        );

        assertEquals(ConstantApplication.PERMISSION_EXITS, exception.getMessage());
        verify(permissionRepository).findByName("TEST_READ");
        //INDICA QUE NUNCA SE EJECUTO METHOD
        verify(permissionRepository, never()).save(any());

    }

    @Test
    void getPermission() {
        //GIVEN
        Long id= 1L;
        PermissionEntity permission= PermissionEntity.builder()
                .id(id)
                .build();
        when(permissionRepository.findById(anyLong())).thenReturn(Optional.of(permission));
        //WHEN
        PermissionDTO result = permissionService.getPermission(permission.getId());
        //THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(permissionRepository).findById(anyLong());
    }

    @Test
    void getPermissionNotFound() {
        Long id= 1L;
        PermissionEntity permission= PermissionEntity.builder()
                .id(id)
                .build();
        when(permissionRepository.findById(anyLong())).thenReturn(Optional.empty());
        //WHEN-THEN
        BadRequestException exception = assertThrows(BadRequestException.class, () ->{
            permissionService.getPermission(id);
        });
        assertEquals(ConstantApplication.PERMISSION_NOT_FOUND, exception.getMessage());
        verify(permissionRepository).findById(anyLong());
    }

    @Test
    void listPermission() {
        //GIVEN
        List<PermissionEntity> list= List.of(
                PermissionEntity.builder()
                        .id(1L)
                        .name("TEST_READ")
                        .description("TEST_READ DESCRIPTION")
                        .build(),
                PermissionEntity.builder()
                        .id(2L)
                        .name("TEST_READ2")
                        .description("TEST_READ DESCRIPTION2")
                        .build()
        );

        when(permissionRepository.findAll()).thenReturn(list);
        //WHEN
        List<PermissionDTO> result = permissionService.listPermission();
        //THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.getFirst().getId());
        verify(permissionRepository).findAll();

    }

    @Test
    void listPermissionBadRequest() {
        //GIVEN
        List<PermissionEntity> list= List.of(
                PermissionEntity.builder()
                        .id(1L)
                        .name("TEST_READ")
                        .description("TEST_READ DESCRIPTION")
                        .build(),
                PermissionEntity.builder()
                        .id(2L)
                        .name("TEST_READ2")
                        .description("TEST_READ DESCRIPTION2")
                        .build()
        );

        when(permissionRepository.findAll()).thenReturn(List.of());
        //WHEN-THEN
        BadRequestException exception = assertThrows(BadRequestException.class, () ->{
            permissionService.listPermission();
        });

        assertEquals(ConstantApplication.PERMISSION_NOT_LIST, exception.getMessage());
        verify(permissionRepository).findAll();

    }

    @Test
    void updatePermission() {
    }

    @Test
    void deletePermission() {
        // GIVEN
        long idDelete= 1L;
        PermissionEntity permissionDelete= PermissionEntity.builder()
                .id(idDelete)
                .build();
        when(permissionRepository.existsById(anyLong())).thenReturn(true);
        //PARA RETORNOS VOID
        doNothing().when(permissionRepository).deleteById(anyLong());
        // WHEN
        permissionService.deletePermission(permissionDelete.getId());
        //THEN
        verify(permissionRepository).existsById(permissionDelete.getId());
        verify(permissionRepository).deleteById(permissionDelete.getId());
    }

    @Test
    void deletePermissionException() {
        // GIVEN
        long idDelete= 1L;
        PermissionEntity permissionDelete= PermissionEntity.builder()
                .id(idDelete)
                .build();
        when(permissionRepository.existsById(anyLong())).thenReturn(false);
        // WHEN
        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                permissionService.deletePermission(permissionDelete.getId())
        );
        //THEN
        assertEquals(ConstantApplication.PERMISSION_NOT_FOUND, exception.getMessage());
        verify(permissionRepository).existsById(permissionDelete.getId());
        verify(permissionRepository, never()).deleteById(permissionDelete.getId());
    }

    @Test
    void count() {
    }
}