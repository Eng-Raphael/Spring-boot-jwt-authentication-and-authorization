package com.raphael.jwttwst.controller;


import com.raphael.jwttwst.entity.User;
import com.raphael.jwttwst.exception.ApiResponse;
import com.raphael.jwttwst.exception.InvalidCredentialsException;
import com.raphael.jwttwst.exception.UserAlreadyExistsException;
import com.raphael.jwttwst.service.JwtService;
import com.raphael.jwttwst.service.UserService;
import com.raphael.jwttwst.utils.AppConstants;
import com.raphael.jwttwst.utils.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("register")
    public ApiResponse<User> register(@RequestBody User user) {
        if(userService.checkIfUserExsists(user.getUsername())){
            throw new UserAlreadyExistsException("User Already Exists");
        }
        return new ApiResponse<>("register success", userService.saveUser(user), "200");
    }

    @PostMapping("login")
    public ApiResponse<String> login(@RequestBody User user){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());
            return new ApiResponse<>("Login success", token, "200");
        }
        else {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("user/welcome")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<String> welcome() {
        return new ApiResponse<>("Success", "Welcome to JWT Authentication USER", "200");

    }

    @GetMapping("admin/welcome")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> adminWelcome() {
        return new ApiResponse<>("Success", "Welcome to JWT Authentication ADMIN", "200");
    }

    @GetMapping("admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PagedResponse<User>> getAllUsers(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size
    ) {
        PagedResponse<User> pagedUsers = userService.getAllUsers(page, size);
        return new ApiResponse<>("Success", pagedUsers, "200");
    }

}
