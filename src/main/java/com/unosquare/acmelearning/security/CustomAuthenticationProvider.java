package com.unosquare.acmelearning.security;

import com.unosquare.acmelearning.model.User;
import com.unosquare.acmelearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication auth){
        String username=auth.getName();
        String pass=auth.getCredentials().toString();

        Optional<User> user=userRepository.findByUsernameAndPassword(username, pass);
        if(!user.isPresent()){
            throw new BadCredentialsException("Authentication failed, verify credentials");
        }
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.get().getType().name() ));
        user.get().setPassword("");
        return new UsernamePasswordAuthenticationToken(user.get(), pass, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
