package com.review.rest;

import com.review.entity.User;
import com.review.exception.EmailExistsException;
import com.review.exception.RegistrationValidationException;
import com.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/rest/api", produces = {"application/JSON"})
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ResponseEntity<User> registerUserAccount (@RequestBody @Valid User userDto, BindingResult result)
            throws RegistrationValidationException, EmailExistsException {
        User registered = null;
        if (!result.hasErrors()) {
            registered = userService.registerNewUserAccount(userDto);
//            try {
//                registered = userService.registerNewUserAccount(userDto);
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
        } else {
            List<ObjectError> allErrors = result.getAllErrors();
            List<String> errors = new ArrayList<>();
            for (ObjectError error : allErrors ) {
                errors.add(error.getObjectName() + " : " + error.getDefaultMessage());
            }
        }

        return ResponseEntity.ok(registered);
    }
}
