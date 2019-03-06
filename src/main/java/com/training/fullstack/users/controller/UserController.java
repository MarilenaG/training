package com.training.fullstack.users.controller;



import com.training.fullstack.admin.service.AdministrativeService;
import com.training.fullstack.users.model.User;
import com.training.fullstack.users.service.SignupService;
import com.training.fullstack.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "http://localhost:8091")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SignupService signupService;

    @Autowired
    private AdministrativeService adminService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserRepresentation usertoSignup) {
        signupService.signup(usertoSignup.toUser(), false);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/confirmRegistration")
    public ResponseEntity<UserRepresentation> confirmRegistration(@RequestParam @NotNull String userName , @RequestParam @NotNull  String registrationCode ) {
        if (! signupService.confirmApplicationMember(userName,registrationCode, false)) {
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

    @GetMapping("/users")
    public ResponseEntity<List<UserRepresentation>> listAllUsers () {
        List<User> allUsers = userService.listAllUsers();
        List<UserRepresentation> results = allUsers.stream()
                .map(UserRepresentation::fromUser)
                .collect(toList());
        return ok(results);
    }

    @PostMapping("/blockUser")
    public ResponseEntity<Void> blockUser(@RequestBody @NotNull User user  ) {
        adminService.blockUser(user.getId());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/unblockUser")
    public ResponseEntity<Void> unblockUser(@RequestBody @NotNull User user ) {
        adminService.unblockUser(user.getId());
        return ResponseEntity.ok().build();
    }
}
