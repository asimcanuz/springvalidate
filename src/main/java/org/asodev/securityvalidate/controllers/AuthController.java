package org.asodev.securityvalidate.controllers;


import org.asodev.securityvalidate.models.User;
import org.asodev.securityvalidate.payload.request.LoginRequest;
import org.asodev.securityvalidate.payload.request.SignupRequest;
import org.asodev.securityvalidate.service.JwtService;
import org.asodev.securityvalidate.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Hello World!";
    }

    @PostMapping("/addNewUser")
    //@ApiModelProperty
    public User addUser(@RequestBody SignupRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody LoginRequest request) {
        var username = userService.getNameWithEmail(request.usernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.password()));
        System.out.println(authentication);
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(username);
            System.out.println("token:" + token);
            return token;
        }
        log.info("invalid username " + request.usernameOrEmail());
        throw new UsernameNotFoundException("invalid username {} " + request.usernameOrEmail());
    }

    @GetMapping("/user")
    public String getUserString() {
        return "This is USER!";
    }

    @GetMapping("/admin")
    public String getAdminString() {
        return "This is ADMIN!";
    }

}
