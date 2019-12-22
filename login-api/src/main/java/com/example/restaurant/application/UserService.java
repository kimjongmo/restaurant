package com.example.restaurant.application;

import com.example.restaurant.domain.User;
import com.example.restaurant.domain.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isExistedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public String encodedPassword(String password){
        return passwordEncoder.encode(password);
    }

    public User authenticate(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(!optionalUser.isPresent())
            throw new EmailNotExistedException(email);

        User user = optionalUser.get();
        String encodedPassword = encodedPassword(password);

        if(!passwordEncoder.matches(password,user.getPassword()))
            throw new PasswordWrongException();

        return user;
    }
}
