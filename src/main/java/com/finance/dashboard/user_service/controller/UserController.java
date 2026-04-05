package com.finance.dashboard.user_service.controller;

import com.finance.dashboard.user_service.dto.UserRequestDTO;
import com.finance.dashboard.user_service.dto.UserResponseDTO;
import com.finance.dashboard.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/debug-create-admin")
    public String createAdmin() {

        UserRequestDTO request = new UserRequestDTO();
        request.setName("Admin");
        request.setEmail("admin@test.com");
        request.setPassword("admin123");
        request.setRole("ADMIN"); 

        userService.createUser(request);

        return "Admin created";
    }

    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO request) {
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
