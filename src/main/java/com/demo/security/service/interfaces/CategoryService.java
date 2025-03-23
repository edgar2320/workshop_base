package com.demo.security.service.interfaces;

import com.demo.security.presentation.dto.request.CategoryCreateDTO;
import com.demo.security.presentation.dto.response.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<CategoryDTO> listCategory(Pageable pageable);
    CategoryDTO findById(Long id);
    CategoryDTO save(CategoryCreateDTO categoryCreateDTO);
    CategoryDTO update(Long id, CategoryCreateDTO categoryCreateDTO);
    void delete(Long id);
}
