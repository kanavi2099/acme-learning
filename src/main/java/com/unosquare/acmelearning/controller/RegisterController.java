package com.unosquare.acmelearning.controller;

import com.unosquare.acmelearning.dto.UserRegistration;
import com.unosquare.acmelearning.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @Operation(description = "Registers a new user.")
    @PostMapping
    public String register(@RequestBody UserRegistration userRegistration){
        return userService.registerUser(userRegistration);
    }
}
