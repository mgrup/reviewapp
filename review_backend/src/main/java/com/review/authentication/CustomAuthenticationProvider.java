package com.review.authentication;

import com.review.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider implements AuthenticationManager {
    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        connect(
                usernamePasswordAuthenticationToken.getPrincipal().toString(),
                usernamePasswordAuthenticationToken.getCredentials().toString());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return connect(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return userService.loadUserByUsername(username);
    }

    private UsernamePasswordAuthenticationToken connect(final String username, final String password){
        UserDetails user = userService.loadUserByUsername(username);
        if(user == null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("User not fund!");
        }
        if(!passwordEncoder.matches(password, userService.getUser(username).getPassword())){
            logger.info("invalid passowrd");
            throw new BadCredentialsException("Invalid password!");
        }

        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
