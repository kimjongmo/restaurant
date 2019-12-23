package com.example.restaurant.application;

import com.example.restaurant.domain.User;
import com.example.restaurant.domain.repo.UserRepository;
import com.example.restaurant.interfaces.EmailExistedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(String email, String name, String password) {

        if(isExistedEmail(email)) throw new EmailExistedException(email);

        User user = User.builder()
                .email(email)
                .name(name)
                .password(encodedPassword(password))
                .level(1L)
                .build();

        return userRepository.save(user);
    }

    private boolean isExistedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public String encodedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
