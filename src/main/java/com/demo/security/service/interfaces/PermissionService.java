package com.demo.security.service.interfaces;

import com.demo.security.presentation.dto.request.PermissionCreateDTO;
import com.demo.security.presentation.dto.response.PermissionDTO;

import java.util.List;

public interface PermissionService {
    PermissionDTO createPermission(PermissionCreateDTO dto);
    PermissionDTO getPermission(Long id);
    List<PermissionDTO> listPermission();
    PermissionDTO updatePermission(Long id, PermissionCreateDTO dto);
    void deletePermission(Long id);
    long count();

}
