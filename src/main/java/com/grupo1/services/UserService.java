package com.grupo1.services;

import com.grupo1.entities.User;
import com.grupo1.enums.UserRole;
import com.grupo1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user, String confirmPassword) {
        if (!user.getPassword().equals(confirmPassword)) {
            throw new RuntimeException("Contraseñas no coinciden.");
        }

        if (!user.getPassword().matches("^(?=.*[0-9])(?=.*[A-Z]).{8,}$")) {
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres, una mayúscula y un número.");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está en uso.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        user.setActive(true);

        return userRepository.save(user);
    }
}
