package com.demo.security.presentation.controllers;

import com.demo.security.presentation.dto.request.CategoryCreateDTO;
import com.demo.security.presentation.dto.response.CategoryDTO;
import com.demo.security.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<Page<CategoryDTO>> listCategory(
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(categoryService.listCategory(pageable));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.save(dto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryCreateDTO dto
    ) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
