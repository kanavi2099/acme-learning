package com.unosquare.acmelearning.dto;

import com.unosquare.acmelearning.enumeration.USER_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {
    @NotBlank
    @Size(min = 3,message = "Use at least 3 characters")
    private String name;
    @NotNull
    private USER_TYPE type;
    @NotBlank
    @Size(min = 3,message = "Use at least 3 characters")
    private String username;
    @NotBlank
    private String password;

}
