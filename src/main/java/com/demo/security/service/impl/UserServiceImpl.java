package com.demo.security.service.impl;

import com.demo.security.persistence.entities.RoleEntity;
import com.demo.security.persistence.entities.UserEntity;
import com.demo.security.persistence.repository.RolRepository;
import com.demo.security.persistence.repository.UserRepository;
import com.demo.security.presentation.dto.request.UserCreateDTO;
import com.demo.security.presentation.dto.request.UserUpdateDTO;
import com.demo.security.presentation.dto.response.RolDTO;
import com.demo.security.presentation.dto.response.UserDTO;
import com.demo.security.presentation.exceptions.BadRequestException;
import com.demo.security.presentation.exceptions.NotFoundException;
import com.demo.security.service.interfaces.UserService;
import com.demo.security.util.ConstantApplication;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    @Override
    public UserDTO createUser(UserCreateDTO dto) {
        if(userRepository.existsByUsername(dto.username())){
            throw new BadRequestException(ConstantApplication.USERNAME_EXIST);
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new BadRequestException(ConstantApplication.USER_EMAIL_EXIST);
        }
        return mapToUserDTO(userRepository.save(mapToUserEntity(dto)));
    }

    @Override
    public UserDTO getUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserDTO)
                .orElseThrow(() -> new NotFoundException(ConstantApplication.USER_NOT_FOUND));
    }

    @Override
    public Page<UserDTO> listUser(Pageable pageable) {
        Page<UserEntity> listUsers = userRepository.findAll(pageable);
        if (listUsers.isEmpty()) {
            throw new NotFoundException(ConstantApplication.USER_NOT_LIST);
        }
        return listUsers.map(this::mapToUserDTO);
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateDTO dto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ConstantApplication.USER_NOT_FOUND));
        if (userRepository.existsByEmailAndIdNot((dto.email()), id)) {
            throw new BadRequestException(ConstantApplication.USERNAME_EXIST);
        }
        if (userRepository.existsByEmailAndIdNot(dto.email(), id)) {
            throw new BadRequestException(ConstantApplication.USER_EMAIL_EXIST);
        }
        user.setId(id);
        user.setEmail(dto.email());
        user.setDateUpdate(LocalDateTime.now());
        if(!dto.rolesIds().isEmpty()){
            user.setRoles(dto.rolesIds().stream()
                    .map(rolId -> rolRepository.findById(rolId)
                            .orElseThrow(() -> new NotFoundException("Rol no encontrado: " + rolId)))
                    .collect(Collectors.toSet()));
        }
        return mapToUserDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
       if(!userRepository.existsById(id)){
           throw new NotFoundException(ConstantApplication.USER_NOT_FOUND);
       }
         userRepository.deleteById(id);
    }

    @Override
    public void changeStatusUser(Long id, Boolean status) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ConstantApplication.USER_NOT_FOUND));
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public Long count() {
        return userRepository.count();
    }

    private UserDTO mapToUserDTO(UserEntity user) {

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .dateCreate(user.getDateCreate())
                .dateUpdate(user.getDateUpdate())
                .roles(user.getRoles().stream()
                        .map(rol -> RolDTO.builder()
                                .id(rol.getId())
                                .name(rol.getName())
                                .description(rol.getDescription())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    private UserEntity mapToUserEntity(UserCreateDTO dto) {

        Set<RoleEntity> roles = dto.rolesIds().stream()
                .map(id -> rolRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Rol no encontrado: " + id)))
                .collect(Collectors.toSet());


        return UserEntity.builder()
                .username(dto.username())
                .password(dto.password())
                .email(dto.email())
                .status(true)
                .dateCreate(LocalDateTime.now())
                .dateUpdate(LocalDateTime.now())
                .roles(roles)
                .build();

    }
}
