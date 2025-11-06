package ru.lazzzer64.bankrest.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazzzer64.bankrest.dto.user.UserRequestDTO;
import ru.lazzzer64.bankrest.dto.user.UserResponseDTO;
import ru.lazzzer64.bankrest.dto.user.UserUpdateDTO;
import ru.lazzzer64.bankrest.entity.User;
import ru.lazzzer64.bankrest.repository.BankAccountRepository;
import ru.lazzzer64.bankrest.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //CREATE - Регистрация нового пользователя
    public UserResponseDTO createUser(@Valid UserRequestDTO registationDTO) {
        if (userRepository.existsByUsername(registationDTO.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким никнеймом уже существует");
        }

        if (userRepository.existsByEmail(registationDTO.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setUsername(registationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registationDTO.getPassword()));
        user.setEmail(registationDTO.getEmail());

        User savedUser = userRepository.save(user);
        return converToDTO(savedUser);
    }

    //READ - Получение пользователя по username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с ником + " + username + " не найден"));
    }

    //READ - Получение пользователя по id для отправки
    public UserResponseDTO getUserDTOById(Long id) {
        User findedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с ID: " + id + " не найден"));

        return converToDTO(findedUser);
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

        User receivedUser =  userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return converToDTO(receivedUser);
    }

    // READ - Получение всех пользователей (только для админов)
    @PreAuthorize("hasRole('ADMIN')")
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
        return converToDTO(updatedUser);
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

    private UserResponseDTO converToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        return dto;
    }
}
