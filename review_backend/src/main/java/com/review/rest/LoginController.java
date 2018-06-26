package com.review.rest;

import com.review.dto.UserDto;
import com.review.entity.User;
import com.review.exception.EmailExistsException;
import com.review.exception.RegistrationValidationException;
import com.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/rest/api", produces = {"application/JSON"})
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ResponseEntity<User> registerUserAccount (@RequestBody @Valid UserDto userDto, BindingResult result)
            throws RegistrationValidationException, EmailExistsException {
        User registered = new User();
        if (!result.hasErrors()) {
            registered.setUsername(userDto.getEmail());
            registered.setFirstName(userDto.getFirstName());
            registered.setLastName(userDto.getLastName());
            registered.setPassword(userDto.getPassword());
            registered.setRole("ADMIN");

            registered = userService.registerNewUserAccount(registered);
        } else {
            List<ObjectError> allErrors = result.getAllErrors();
            List<String> errors = new ArrayList<>();
            for (ObjectError error : allErrors ) {
                errors.add(error.getObjectName() + " : " + error.getDefaultMessage());
            }
        }

        return ResponseEntity.ok(registered);
    }
    @GetMapping("/user")
    @ResponseBody
    public Principal user(Principal principal, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return principal;
    }

//    @RequestMapping("/token")
//    public Map<String,String> token(HttpSession session) {
//        return Collections.singletonMap("token", session.getId());
//    }
//    @Bean
//    HeaderHttpSessionStrategy sessionStrategy() {
//        return new HeaderHttpSessionStrategy();
//    }
}
