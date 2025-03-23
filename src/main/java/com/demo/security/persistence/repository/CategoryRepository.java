package com.demo.security.persistence.repository;

import com.demo.security.persistence.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);
    boolean existsByIdAndName(Long id, String name);
    @Query(value = "SELECT c FROM CategoryEntity c WHERE c.status = true")
    Page<CategoryEntity> findAllCategoryValidate(Pageable pageable);
}
