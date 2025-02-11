package com.demo.security.service.interfaces;

import com.demo.security.presentation.dto.request.RolCreateDTO;
import com.demo.security.presentation.dto.response.RolDTO;

import java.util.List;
import java.util.Optional;

public interface RolService {
    RolDTO createRol(RolCreateDTO dto);
    RolDTO getRol(Long id);
    List<RolDTO> listRoles();
    RolDTO updateRol(Long id, RolCreateDTO dto);
    void deleteRol(Long id);
    Optional<RolDTO> findByName(String name);
    long count();
}
