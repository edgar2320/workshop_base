package com.demo.security.presentation.controllers;

import com.demo.security.presentation.dto.request.UserCreateDTO;
import com.demo.security.presentation.dto.request.UserUpdateDTO;
import com.demo.security.presentation.dto.response.UserDTO;
import com.demo.security.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(dto));
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<UserDTO> gerUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<UserDTO>> listUsers(
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(userService.listUser(pageable));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto
    ) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> changeStatusUser(
            @PathVariable Long id,
            @RequestParam Boolean status) {
        userService.changeStatusUser(id, status);
        return ResponseEntity.ok().build();
    }
}
