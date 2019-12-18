package com.example.restaurant.application;

import com.example.restaurant.domain.User;
import com.example.restaurant.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(String name, String email) {
        User user = User.builder()
                .name(name)
                .email(email)
                .level(1L)
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, String name, String email, Long level) {
        // TODO: 2019-12-18 UserNotFound 예외 처리하기
        User user = userRepository.findById(id).get();
        user.setName(name);
        user.setEmail(email);
        user.setLevel(level);
        return user;
    }
    @Transactional
    public User deactivateUser(Long id){
        User user = userRepository.findById(id).get();
        user.deactivate();
        return user;
    }
}
