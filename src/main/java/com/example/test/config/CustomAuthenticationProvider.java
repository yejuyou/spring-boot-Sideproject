package com.example.test.config;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService ;

    public CustomAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UsernamePasswordAuthenticationToken authenticate(org.springframework.security.core.Authentication authentication)  {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = userService.loadUserByUsername(email);
        if (!checkPassword(password, userDetails.getPassword()) || !userDetails.isEnabled()) {
            throw new BadCredentialsException(email);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }



    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    private boolean checkPassword(String loginPassword, String dbPassword) {
        return BCrypt.checkpw(loginPassword, dbPassword);
    }



}
