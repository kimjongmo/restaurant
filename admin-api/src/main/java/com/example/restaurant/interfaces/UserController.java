package com.example.restaurant.interfaces;

import com.example.restaurant.application.UserService;
import com.example.restaurant.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity create(@RequestBody User resource ) throws URISyntaxException {
        String name = resource.getName();
        String email = resource.getEmail();
        User user = userService.addUser(name, email);
        String uri = "/users/"+user.getId();
        return ResponseEntity.created(new URI(uri)).body("");
    }

    @PatchMapping("/users/{id}")
    public String update(@PathVariable Long id, @RequestBody User resource ) throws URISyntaxException {
        String name = resource.getName();
        String email = resource.getEmail();
        Long level = resource.getLevel();

        User user = userService.updateUser(id, name, email,level);

        return "";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id){
        userService.deactivateUser(id);
        return "";
    }
}
