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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    //Email, Name, Password
    @Test
    public void createWithValidData() throws Exception {

        String email = "tester@example.com";
        String password = "test";
        User mockUser = User.builder().password("ACCESSTOKE").build();
        given(userService.authenticate(email,password)).willReturn(mockUser);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"tester@example.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string("{\"accessToken\":\"ACCESSTOKE\"}"))
        ;

        verify(userService).authenticate(eq(email),eq(password));
    }

    @Test
    public void createWithInvalidData() throws Exception {
        String email = "tester@example.com";
        String password = "x";
        given(userService.authenticate(email,password)).willThrow(new PasswordWrongException());

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\""+email+"\",\"password\":\""+password+"\"}"))
                .andExpect(status().isBadRequest())
        ;
        verify(userService).authenticate(eq(email),eq(password));
    }

    @Test
    public void createWithNotExistedEmail() throws Exception {
        String email = "x@example.com";
        String password = "test";
        given(userService.authenticate(email,password)).willThrow(new EmailNotExistedException(email));

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\""+email+"\",\"password\":\""+password+"\"}"))
                .andExpect(status().isBadRequest())
        ;
        verify(userService).authenticate(eq(email),eq(password));
    }

//    @Test
//    public void createWithExistedEmail() throws Exception {
//        String password = "test";
//        String name = "tester";
//        String email = "tester@example.com";
//        given(userService.registerUser(email, name, password)).willThrow(new EmailExistedException(email));
//
//        mvc.perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content("{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"password\":\"" + password + "\"}"))
//                .andExpect(status().isBadRequest())
//        ;
//
//        verify(userService).registerUser(email, name, password);
//
//    }
}