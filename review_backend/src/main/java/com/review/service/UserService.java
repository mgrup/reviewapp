package com.review.service;

import com.review.entity.User;
import com.review.exception.EmailExistsException;
import com.review.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService{
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
    };

    @Transactional
    @Override
    public User registerNewUserAccount(User user) throws EmailExistsException {
        if (emailExist(user.getEmail())) {
            throw new EmailExistsException("There is an account with that email address:" + user.getEmail());
        }
        User userAccount = new User();
        userAccount.setFirstName(user.getFirstName());
        userAccount.setLastName(user.getLastName());

        userAccount.setPassword(passwordEncoder.encode(user.getPassword()));
        userAccount.setMatchingPassword(userAccount.getPassword());

        userAccount.setEmail(user.getEmail());
        userRepository.save(userAccount);
        return userAccount;
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }

}