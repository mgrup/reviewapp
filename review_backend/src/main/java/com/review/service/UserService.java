package com.review.service;

import com.review.entity.MyUserPrincipal;
import com.review.entity.User;
import com.review.exception.EmailExistsException;
import com.review.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService /*implements IUserService*/ implements UserDetailsService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user){
        return userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Transactional
//    @Override
    public User registerNewUserAccount(User user) throws EmailExistsException {
        if (emailExist(user.getUsername())) {
            throw new EmailExistsException("There is an account with that email address:" + user.getEmail());
        }
//        User userAccount = new User();
//        userAccount.setFirstName(user.getFirstName());
//        userAccount.setLastName(user.getLastName());
//
//        userAccount.setPassword(passwordEncoder.encode(user.getPassword()));
//        userAccount.setMatchingPassword(userAccount.getPassword());
//
//        userAccount.setUsername(user.getUsername());
//        userAccount.setRole(user.getRole());
//        userRepository.save(userAccount);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        logger.info("new registered user={}", user);
        return user;
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

}