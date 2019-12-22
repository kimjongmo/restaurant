package com.example.restaurant.interfaces;

import com.example.restaurant.application.EmailNotExistedException;
import com.example.restaurant.application.PasswordWrongException;
import com.example.restaurant.application.UserService;
import com.example.restaurant.domain.User;
import com.example.restaurant.utils.JwtUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtils jwtUtils;

    //Email, Name, Password
    @Test
    public void createWithValidData() throws Exception {

        String email = "tester@example.com";
        String password = "test";
        String name = "tester";
        Long id = 1004L;
        Long level = 1L;

        User mockUser = User.builder().id(id).name(name).level(level).build();
        BDDMockito.given(userService.authenticate(email,password)).willReturn(mockUser);
        BDDMockito.given(jwtUtils.createToken(id,name, null)).willReturn("header.payload.signature");

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"tester@example.com\",\"password\":\"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("location", "/session"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"accessToken\":\"header.payload.signature\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(".")))
        ;

        Mockito.verify(userService).authenticate(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    @Test
    public void createWithValidDataAndRestaurantOwner() throws Exception {

        String email = "tester@example.com";
        String password = "test";
        String name = "tester";
        Long id = 1004L;
        Long level = 50L;

        User mockUser = User.builder().id(id).name(name).level(level).restaurantId(369L).build();
        BDDMockito.given(userService.authenticate(email,password)).willReturn(mockUser);
        BDDMockito.given(jwtUtils.createToken(id,name, 369L)).willReturn("header.payload.signature");

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"tester@example.com\",\"password\":\"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("location", "/session"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"accessToken\":\"header.payload.signature\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(".")))
        ;

        Mockito.verify(userService).authenticate(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }


    @Test
    public void createWithInvalidData() throws Exception {
        String email = "tester@example.com";
        String password = "x";
        BDDMockito.given(userService.authenticate(email,password)).willThrow(new PasswordWrongException());

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\""+email+"\",\"password\":\""+password+"\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
        ;
        Mockito.verify(userService).authenticate(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    @Test
    public void createWithNotExistedEmail() throws Exception {
        String email = "x@example.com";
        String password = "test";
        BDDMockito.given(userService.authenticate(email,password)).willThrow(new EmailNotExistedException(email));

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\""+email+"\",\"password\":\""+password+"\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
        ;
        Mockito.verify(userService).authenticate(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
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