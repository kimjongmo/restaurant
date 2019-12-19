package com.example.restaurant.interfaces;

import com.example.restaurant.application.UserService;
import com.example.restaurant.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    //Email, Name, Password
    @Test
    public void create() throws Exception {

        String email = "tester@example.com";
        String name = "tester";
        String password = "test";

        User mockUser = User.builder()
                .id(1004L)
                .name(name)
                .email(email)
                .password(password)
                .build();

        given(userService.registerUser(email,name,password)).willReturn(mockUser);


        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"tester@example.com\",\"name\":\"tester\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/users/1004"))
        ;

        verify(userService).registerUser(email,name,password);
    }

    @Test
    public void createWithExistedEmail() throws Exception {
        String password = "test";
        String name = "tester";
        String email = "tester@example.com";
        given(userService.registerUser(email,name,password)).willThrow(new EmailExistedException(email));

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\""+email+"\",\"name\":\""+name+"\",\"password\":\""+password+"\"}"))
                .andExpect(status().isBadRequest())
        ;

        verify(userService).registerUser(email,name,password);

    }
}