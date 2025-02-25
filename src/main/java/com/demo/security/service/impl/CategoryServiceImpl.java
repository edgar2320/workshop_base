package com.demo.security.service.impl;

import com.demo.security.persistence.entities.CategoryEntity;
import com.demo.security.persistence.repository.CategoryRepository;
import com.demo.security.presentation.dto.request.CategoryCreateDTO;
import com.demo.security.presentation.dto.response.CategoryDTO;
import com.demo.security.presentation.exceptions.NotFoundException;
import com.demo.security.service.interfaces.CategoryService;
import com.demo.security.util.ConstantApplication;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDTO> listCategory(Pageable pageable) {
        Page<CategoryEntity> categoryEntities = categoryRepository.findAllCategoryValidate(pageable);
        if(categoryEntities.isEmpty()){
            throw new NotFoundException(ConstantApplication.CATEGORY_NOT_FOUND);
        }
        return categoryEntities.map(this::mapToCategoryDTO);
    }

    @Override
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::mapToCategoryDTO)
                .orElseThrow(() -> new NotFoundException(ConstantApplication.CATEGORY_NOT_FOUND));
    }

    @Override
    public CategoryDTO save(CategoryCreateDTO categoryCreateDTO) {
        if(categoryRepository.existsByName(categoryCreateDTO.name())){
            throw new NotFoundException(ConstantApplication.CATEGORY_NAME_EXIST);
        }
        return mapToCategoryDTO(categoryRepository.save(mapToCategoryEntity(categoryCreateDTO)));
    }

    @Override
    public CategoryDTO update(Long id, CategoryCreateDTO categoryCreateDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(ConstantApplication.CATEGORY_NOT_FOUND));
        if(!categoryRepository.existsById(id)){
            throw new NotFoundException(ConstantApplication.CATEGORY_NOT_FOUND);
        }
        if(categoryRepository.existsByIdAndName(id,categoryCreateDTO.name())){
            throw new NotFoundException(ConstantApplication.CATEGORY_NAME_EXIST);
        }
        categoryEntity.setName(categoryCreateDTO.name());
        categoryEntity.setDescription(categoryCreateDTO.description());
        categoryEntity.setStatus(categoryCreateDTO.status());
        return mapToCategoryDTO(categoryRepository.save(categoryEntity));
    }

    @Override
    public void delete(Long id) {
        if(!categoryRepository.existsById(id)){
            throw new NotFoundException(ConstantApplication.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToCategoryDTO(CategoryEntity categoryEntity){
        return CategoryDTO.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .status(categoryEntity.isStatus())
                .build();

    }
    private CategoryEntity mapToCategoryEntity(CategoryCreateDTO categoryCreateDTO){
        return CategoryEntity.builder()
                .name(categoryCreateDTO.name())
                .description(categoryCreateDTO.description())
                .status(categoryCreateDTO.status())
                .build();
    }

}
