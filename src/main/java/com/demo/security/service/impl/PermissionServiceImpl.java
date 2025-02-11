package com.demo.security.service.impl;

import com.demo.security.persistence.entities.PermissionEntity;
import com.demo.security.persistence.repository.PermissionRepository;
import com.demo.security.presentation.dto.request.PermissionCreateDTO;
import com.demo.security.presentation.dto.response.PermissionDTO;
import com.demo.security.presentation.exceptions.BadRequestException;
import com.demo.security.service.interfaces.PermissionService;
import com.demo.security.util.ConstantApplication;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    @Override
    public PermissionDTO createPermission(PermissionCreateDTO dto) {
        if (permissionRepository.findByName(dto.name()).isPresent()) {
            throw new BadRequestException(ConstantApplication.PERMISSION_EXITS);
        }
        PermissionEntity entity = mapPermissionEntity(dto);
        return mapPermissionDTO(permissionRepository.save(entity));
    }

    @Override
    public PermissionDTO getPermission(Long id) {
         return  permissionRepository.findById(id)
                 .map(this::mapPermissionDTO)
                 .orElseThrow(() -> new BadRequestException(ConstantApplication.PERMISSION_NOT_FOUND));
    }

    @Override
    public List<PermissionDTO> listPermission() {
        List<PermissionEntity> listPermission = permissionRepository.findAll();
        if(listPermission.isEmpty()){
            throw new BadRequestException(ConstantApplication.PERMISSION_NOT_LIST);
        }
        return listPermission.stream()
                .map(this::mapPermissionDTO)
                .toList();
    }

    @Override
    public PermissionDTO updatePermission(Long id, PermissionCreateDTO dto) {
        PermissionEntity permissionExist = permissionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ConstantApplication.PERMISSION_NOT_FOUND));
        if (permissionRepository.existsByNameAndIdNot(dto.name(), id)) {
            throw new BadRequestException(ConstantApplication.PERMISSION_EXITS);
        }
        permissionExist.setName(dto.name());
        permissionExist.setDescription(dto.description());
        return mapPermissionDTO(permissionRepository.save(permissionExist));
    }

    @Override
    public void deletePermission(Long id) {
       if(!permissionRepository.existsById(id)) {
              throw new BadRequestException(ConstantApplication.PERMISSION_NOT_FOUND);
       }
         permissionRepository.deleteById(id);
    }

    @Override
    public long count() {
        return permissionRepository.count();
    }

    private PermissionDTO mapPermissionDTO(PermissionEntity entity) {
        return PermissionDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    private PermissionEntity mapPermissionEntity(PermissionCreateDTO dto) {
        return PermissionEntity.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
    }
}
