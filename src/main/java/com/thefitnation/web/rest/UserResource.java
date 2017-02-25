package com.thefitnation.web.rest;

import com.thefitnation.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by michael on 2/19/17.
 */
@RestController(value = "/api/v1/user")
public class UserResource {


    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

}
