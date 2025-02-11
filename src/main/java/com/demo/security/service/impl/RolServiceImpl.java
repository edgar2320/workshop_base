package com.demo.security.service.impl;

import com.demo.security.persistence.entities.PermissionEntity;
import com.demo.security.persistence.entities.RoleEntity;
import com.demo.security.persistence.repository.PermissionRepository;
import com.demo.security.persistence.repository.RolRepository;
import com.demo.security.presentation.dto.request.RolCreateDTO;
import com.demo.security.presentation.dto.response.PermissionDTO;
import com.demo.security.presentation.dto.response.RolDTO;
import com.demo.security.presentation.exceptions.BadRequestException;
import com.demo.security.service.interfaces.RolService;
import com.demo.security.util.ConstantApplication;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RolServiceImpl  implements RolService {
    private final RolRepository rolRepository;
    private final PermissionRepository permissionRepository;
    @Override
    public RolDTO createRol(RolCreateDTO dto) {
        //VALIDANDO SI EL ROL NO EXISTE
        if (rolRepository.existsByName(dto.name())) {
            throw new BadRequestException(ConstantApplication.ROLE_NOT_FOUND);
        }
        //CRANDO EL ROL
        RoleEntity rol = mapToRolEntity(dto);
        // SE MAPEA EL ROL Y SE GUARDA
         return mapToRolDTO(rolRepository.save(rol));
    }

    @Override
    public RolDTO getRol(Long id) {
        return rolRepository.findById(id)
                .map(this::mapToRolDTO)
                .orElseThrow(() -> new BadRequestException(ConstantApplication.ROLE_NOT_FOUND));
    }

    @Override
    public List<RolDTO> listRoles() {
        List<RoleEntity> roleList = rolRepository.findAll();
        if (roleList.isEmpty()) {
            throw new BadRequestException(ConstantApplication.ROLE_NOT_LIST);
        }
        return roleList.stream()
                .map(this::mapToRolDTO)
                .toList();
    }

    @Override
    public RolDTO updateRol(Long id, RolCreateDTO dto) {
        RoleEntity rolExist= rolRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ConstantApplication.ROLE_NOT_FOUND));
        if (rolRepository.existsByNameAndIdNot(dto.name(), id)) {
            throw new BadRequestException(ConstantApplication.ROLE_NOT_FOUND);
        }
        RoleEntity rol = mapToRolEntity(dto);
        rol.setId(rolExist.getId());
        return mapToRolDTO(rolRepository.save(rol));
    }

    @Override
    public void deleteRol(Long id) {
          if(!rolRepository.existsById(id)) {
              throw new BadRequestException(ConstantApplication.ROLE_NOT_FOUND);
          }
          rolRepository.deleteById(id);
    }

    @Override
    public Optional<RolDTO> findByName(String name) {
        return Optional.ofNullable(rolRepository.findByName(name)
                .map(this::mapToRolDTO)
                .orElseThrow(() -> new BadRequestException(ConstantApplication.ROLE_NOT_FOUND)));
    }

    @Override
    public long count() {
        return rolRepository.count();
    }

    private RolDTO mapToRolDTO(RoleEntity rol) {
        return RolDTO.builder()
                .id(rol.getId())
                .name(rol.getName())
                .description(rol.getDescription())
                .permissions(rol.getPermissions().stream()
                        .map(p -> PermissionDTO.builder()
                                .id(p.getId())
                                .name(p.getName())
                                .description(p.getDescription())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    private RoleEntity mapToRolEntity(RolCreateDTO dto) {
        RoleEntity rol = new RoleEntity();
        rol.setName(dto.name());
        rol.setDescription(dto.description());
        if (dto.permissionIds() != null && !dto.permissionIds().isEmpty()) {
            Set<PermissionEntity> permissions = dto.permissionIds().stream()
                    .map(p -> permissionRepository.findById(p)
                            .orElseThrow(() -> new BadRequestException(ConstantApplication.PERMISSION_NOT_FOUND)))
                    .collect(Collectors.toSet());
            rol.setPermissions(permissions);
        }
        return rol;
    }

}
