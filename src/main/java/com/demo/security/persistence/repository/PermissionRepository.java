package com.demo.security.persistence.repository;

import com.demo.security.persistence.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository  extends JpaRepository<PermissionEntity, Long> {
    Optional<PermissionEntity> findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
