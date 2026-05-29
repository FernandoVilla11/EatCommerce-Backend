package com.eatcommerce.eatcommerce.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.eatcommerce.eatcommerce.DTO.UserDTO;
import com.eatcommerce.eatcommerce.DTO.UserLoginResponse;
import com.eatcommerce.eatcommerce.service.AuditService;
import com.eatcommerce.eatcommerce.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuditService auditService;

    @PostMapping("/create-user")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody UserDTO request,
            Authentication auth) {
        UserDTO created = userService.createUser(request);
        auditService.log(auth.getName(), "CREATE_USER", "User",
                String.valueOf(created.getUserId()),
                "Creó usuario: " + created.getUserName() + " con rol: " + created.getRole());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/edit-user")
    public ResponseEntity<UserDTO> editUser(
            @RequestParam Long userId,
            @RequestBody UserDTO request,
            Authentication auth) {
        UserDTO updated = userService.editUser(userId, request);
        auditService.log(auth.getName(), "UPDATE_USER", "User",
                String.valueOf(userId),
                "Editó usuario: " + updated.getUserName());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserDTO> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(
            @RequestParam Long userId,
            Authentication auth) {
        auditService.log(auth.getName(), "DELETE_USER", "User",
                String.valueOf(userId),
                "Eliminó usuario ID: " + userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<UserLoginResponse> getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).build();
        }
        String token = authHeader.substring(7);
        UserLoginResponse response = userService.getUserFromToken(token);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(response);
    }
}