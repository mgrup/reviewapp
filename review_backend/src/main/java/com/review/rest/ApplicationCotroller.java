package com.review.rest;

import com.review.entity.User;
import com.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/rest/api"/*, produces = {"application/JSON"}*/)
public class ApplicationCotroller {

    private final UserService userService;

    @Autowired
    public ApplicationCotroller(UserService userService){
        this.userService = userService;
    }


    @RequestMapping(value="/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity login(@RequestParam("email") String email){
        User user = userService.create(new User(email));
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ResponseEntity getAllUsers(){
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value="/testa")
    public String test(HttpServletRequest request){
        System.out.println(request.getRemoteAddr());
        return "Done!";
    }

}
