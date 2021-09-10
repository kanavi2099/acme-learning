package com.unosquare.acmelearning.dto;

import com.unosquare.acmelearning.enumeration.USER_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {
    private String name;
    private USER_TYPE type;
    private String username;
    private String password;

}
