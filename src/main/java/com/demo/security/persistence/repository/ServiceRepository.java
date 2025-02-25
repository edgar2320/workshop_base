package com.demo.security.persistence.repository;

import com.demo.security.persistence.entities.CategoryEntity;
import com.demo.security.persistence.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    boolean existsByCategoryAndIdNot(CategoryEntity categoryEntity, Long id);
}
