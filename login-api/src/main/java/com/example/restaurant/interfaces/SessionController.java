package com.example.restaurant.interfaces;

import com.example.restaurant.application.UserService;
import com.example.restaurant.domain.User;
import com.example.restaurant.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(@RequestBody SessionRequestDto request) throws URISyntaxException {

        String email = request.getEmail();

        String password = request.getPassword();

        User user = userService.authenticate(email, password);

        String accessToken = jwtUtils.createToken(
                user.getId(),
                user.getName(),
                user.isRestaurantOwner() ? user.getRestaurantId() : null);

        SessionResponseDto sessionResponseDto = SessionResponseDto.builder()
                .accessToken(accessToken)
                .build();

        return ResponseEntity.created(new URI("/session")).body(sessionResponseDto);
    }
}
