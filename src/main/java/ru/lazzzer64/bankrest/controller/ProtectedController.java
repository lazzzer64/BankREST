package ru.lazzzer64.bankrest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lazzzer64.bankrest.dto.userDTO.UserRequestDTO;

@RestController
@RequestMapping("/api/users")
public class ProtectedController {

    @GetMapping("/me")
    public ResponseEntity<UserRequestDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Логика получения профиля пользователя
        UserRequestDTO profile = new UserRequestDTO(username, "user@example.com");

        return ResponseEntity.ok(profile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Admin access granted");
    }
}
