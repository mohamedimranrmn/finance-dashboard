package com.finance.dashboard.user_service.service;


import com.finance.dashboard.user_service.dto.UserRequestDTO;
import com.finance.dashboard.user_service.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    void deleteUser(Long id);
}
