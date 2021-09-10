package com.unosquare.acmelearning.model;

import com.unosquare.acmelearning.enumeration.USER_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    private long id;
    private String username;
    private String password;
    private USER_TYPE type;



}
