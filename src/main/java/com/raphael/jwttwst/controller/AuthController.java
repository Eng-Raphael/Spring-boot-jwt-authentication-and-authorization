package com.raphael.jwttwst.controller;


import com.raphael.jwttwst.entity.User;
import com.raphael.jwttwst.service.JwtService;
import com.raphael.jwttwst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("register")
    public User register(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("login")
    public String login(@RequestBody User user){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        else
            return "Login Failed";

    }

    @GetMapping("user/welcome")
    @PreAuthorize("hasRole('USER')")
    public String welcome() {
        return "Welcome to JWT Authentication USER";
    }

    @GetMapping("admin/welcome")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminWelcome() {
        return "Welcome to JWT Authentication ADMIN";
    }

}
