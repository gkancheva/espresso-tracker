package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.web.UserRequest;
import com.softuni.espresso.tracker.model.web.UserResponse;
import com.softuni.espresso.tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final UserService userService;


    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@Valid @RequestBody UserRequest userRequest) {
        userService.register(userRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserRequest user) {
        UserDetails loggedInUser = userService.login(user);
        UserResponse response = UserResponse.builder()
                .username(loggedInUser.getUsername())
                .build();
        return ResponseEntity.ok(response);
    }

}
