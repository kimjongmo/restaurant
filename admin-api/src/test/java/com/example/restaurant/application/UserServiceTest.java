package com.example.restaurant.application;

import com.example.restaurant.domain.User;
import com.example.restaurant.domain.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
        userRepositorySetup();
    }

    private void userRepositorySetup(){
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder().name("test").email("test@example.com").level(1L).build());
        mockUsers.add(User.builder().name("test1").email("test1@example.com").level(100L).build());

        given(userRepository.findAll()).willReturn(mockUsers);
    }

    @Test
    public void getUsers(){
        List<User> users = userService.getUsers();
        User user = users.get(0);

        assertThat(user.getName(),is("test"));
        assertThat(user.getEmail(),is("test@example.com"));
        assertThat(user.getLevel(),is(1L));
    }

    @Test
    public void create(){
        String name = "admin";
        String email = "admin@example.com";

        User mockUser = User.builder().name(name).email(email).build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(name, email);

        assertThat(user.getName(),is(name));
    }

    @Test
    public void update(){
        Long id = 1004L;
        String name = "admin";
        String email = "admin@example.com";
        Long level = 100L;
        User mockUser = User.builder().id(id).name("test").email("test@example.com").level(1L).build();
        given(userRepository.findById(1004L)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id,name,email,level);
        verify(userRepository).findById(eq(id));


        assertThat(user.getName(),is(name));
        assertThat(user.getId(),is(id));
        assertThat(user.getEmail(),is(email));
        assertThat(user.isAdmin(),is(true));
    }

    @Test
    public void deactivate(){
        Long id = 1004L;
        User mockUser = User.builder().id(id).name("test").email("test@example.com").level(1L).build();
        given(userRepository.findById(1004L)).willReturn(Optional.of(mockUser));

        User user = userService.deactivateUser(id);

        verify(userRepository).findById(1004L);

        assertThat(user.isActive(),is(false));

    }

}