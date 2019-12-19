package com.example.restaurant.application;

import com.example.restaurant.interfaces.EmailExistedException;
import com.example.restaurant.domain.User;
import com.example.restaurant.domain.UserRepository;
import com.example.restaurant.interfaces.EmailNotExistedException;
import com.example.restaurant.interfaces.PasswordWrongException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
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
        log.info("encoding... : "+password);
        return passwordEncoder.encode(password);
    }

    public User authenticate(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(!optionalUser.isPresent())
            throw new EmailNotExistedException(email);

        User user = optionalUser.get();
        String encodedPassword = encodedPassword(password);

        log.info("user password : "+user.getPassword());
        log.info("request password : "+encodedPassword);

        if(!passwordEncoder.matches(password,user.getPassword()))
            throw new PasswordWrongException();

        return user;
    }
}
