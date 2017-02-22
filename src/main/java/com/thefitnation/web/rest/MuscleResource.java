package com.thefitnation.web.rest;

import com.thefitnation.service.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by michael on 2/21/17.
 */
@RestController(value = "/api/v1")
public class MuscleResource {

    private final MuscleService muscleService;


    public MuscleResource(MuscleService muscleService) {
        this.muscleService = muscleService;
    }



}
