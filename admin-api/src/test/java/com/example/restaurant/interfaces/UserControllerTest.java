package com.example.restaurant.interfaces;

import com.example.restaurant.application.UserService;
import com.example.restaurant.domain.User;
import org.apache.catalina.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {

        List<User> users = new ArrayList<>();

        users.add(User.builder().name("test").email("test@example.com").level(1L).build());

        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")))
        ;
    }

    @Test
    public void create() throws Exception {

//        given(userService.addUser(any())).will(invocation -> {
//            User user = invocation.getArgument(0);
//            user.setId(1L);
//            return user;
//        });
        given(userService.addUser("admin", "admin@example.com")).willReturn(
                User.builder().name("admin").email("admin@example.com").id(1L).build());

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"admin@example.com\",\"name\":\"admin\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/users/1"))
        ;

        verify(userService).addUser("admin","admin@example.com");

    }

    @Test
    public void update() throws Exception {
        Long id = 1004L;
        String name = "admin";
        String email = "admin@example.com";
        Long level = 100L;

        User user = User.builder().id(id).name(name).email(email).level(level).build();

        given(userService.updateUser(id, name, email, level)).willReturn(user);

        mvc.perform(patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":1004,\"email\":\"admin@example.com\",\"name\":\"admin\",\"level\":100}"))
                .andExpect(status().isOk())
        ;

        verify(userService).updateUser(id, name, email, level);
    }

    @Test
    public void deactivate() throws Exception {
        mvc.perform(delete("/users/1004"))
                .andExpect(status().isOk());

        verify(userService).deactivateUser(1004L);

    }
}