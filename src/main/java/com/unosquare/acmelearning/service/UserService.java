package com.unosquare.acmelearning.service;

import com.unosquare.acmelearning.dto.UserRegistration;
import com.unosquare.acmelearning.model.User;

public interface UserService {
    String registerUser(UserRegistration newUser);
}
