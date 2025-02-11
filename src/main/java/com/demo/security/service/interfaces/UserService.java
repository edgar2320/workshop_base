package com.demo.security.service.interfaces;

import com.demo.security.presentation.dto.request.UserCreateDTO;
import com.demo.security.presentation.dto.request.UserUpdateDTO;
import com.demo.security.presentation.dto.response.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDTO createUser(UserCreateDTO dto);
    UserDTO getUser(Long id);
    Page<UserDTO> listUser(Pageable pageable);
    UserDTO updateUser(Long id, UserUpdateDTO dto);
    void deleteUser(Long id);
    void changeStatusUser(Long id, Boolean status);
    Long count();
}
