package ru.lazzzer64.bankrest.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazzzer64.bankrest.dto.userDTO.UserRegistrationDTO;
import ru.lazzzer64.bankrest.dto.userDTO.UserResponseDTO;
import ru.lazzzer64.bankrest.dto.userDTO.UserUpdateDTO;
import ru.lazzzer64.bankrest.entity.User;
import ru.lazzzer64.bankrest.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    private final CardService cardService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    //CREATE - Регистрация нового пользователя
    public UserResponseDTO createUser(@Valid UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким никнеймом уже существует");
        }

        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setEmail(registrationDTO.getEmail());

        User savedUser = userRepository.save(user);
        cardService.createCard(savedUser.getId());

        return convertToDTO(savedUser);
    }

    //READ - Получение пользователя по username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с ником + " + username + " не найден"));
    }

    //READ - для SpringSecurity
    public User getByLogin(@NonNull String login) {
        return userRepository.findByUsername(login)
                .orElseThrow(() -> new RuntimeException("Пользователь с никнеймом: " + login + " не найден"));
    }

    //READ - Получение пользователя по id для отправки
    public UserResponseDTO getUserDTOById(Long id) {
        User findedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с ID: " + id + " не найден"));

        return convertToDTO(findedUser);
    }

    //READ - Получение пользователя по id для работы
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с ID: " + id + " не найден"));
    }

    // READ - Получение текущего аутентифицированного пользователя
    public UserResponseDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Пользователь не аутентифицирован");
        }

        User receivedUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return convertToDTO(receivedUser);
    }

    // READ - Получение всех пользователей (только для админов)
//    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // UPDATE - Обновление данных пользователя
    public UserResponseDTO updateUser(Long userId, UserUpdateDTO updateDTO) {
        User user = getUserById(userId);

        if (updateDTO.getEmail() != null &&
                !updateDTO.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(updateDTO.getEmail())) {
            throw new IllegalArgumentException("Email уже зарегестрирован");
        }

        if (updateDTO.getUsername() != null &&
                !updateDTO.getUsername().equals(user.getUsername()) &&
                userRepository.existsByUsername(updateDTO.getUsername())) {
            throw new IllegalArgumentException("Username уже существует");
        }

        if (updateDTO.getUsername() != null) {
            user.setUsername(updateDTO.getUsername());
        }
        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    // DELETE - Удаление пользователя
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByUsername(email);
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());

        return dto;
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
