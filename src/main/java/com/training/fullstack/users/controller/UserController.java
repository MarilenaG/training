package com.training.fullstack.users.controller;


import com.training.fullstack.users.model.User;
import com.training.fullstack.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserRepresentation usertoSignup) {
        userService.signup(usertoSignup.toUser());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/confirmRegistration")
    public ResponseEntity<UserRepresentation> confirmRegistration(@RequestParam @NotNull String userName , @RequestParam @NotNull  String registrationCode ) {
        if (! userService.confirmUser(userName,registrationCode)) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, "UserName/registration code mismatch");
        }
        User registeredUser = userService.getUser(userName);
        return new ResponseEntity(UserRepresentation.fromUser(registeredUser), HttpStatus.OK);


    }

   @GetMapping("/hello")
   public ResponseEntity<String> hello(){
        return new ResponseEntity("hello there", HttpStatus.OK);
   }

}
