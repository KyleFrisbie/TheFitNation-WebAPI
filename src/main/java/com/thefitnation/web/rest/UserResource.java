package com.thefitnation.web.rest;

import com.thefitnation.model.User;
import com.thefitnation.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return userService.getAllUsers();
    }

//    @GetMapping(path = "/{id}")
//    ResponseEntity<User> findOneUserById(@PathVariable Long id) {
//        return null;
//    }

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
