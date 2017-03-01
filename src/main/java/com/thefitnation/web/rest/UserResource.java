package com.thefitnation.web.rest;

import com.thefitnation.model.User;
import com.thefitnation.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    // TODO: 2/28/2017 cant get all users on /user, must use /user/
    @GetMapping(path = "/")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    // TODO: 2/28/2017 error with getAllUsers on "/" and findOneUserById on "/{id}"
//    @GetMapping(path = "/{id}")
//    ResponseEntity<User> findOneUserById(@PathVariable Long id) {
//        return null;
//    }

    // TODO: 2/28/2017 cannot create user, LocalDate constructor error
    @PostMapping(path = "/create")
    public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
        return userService.createUser(user);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        return null;
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return null;
    }
}
