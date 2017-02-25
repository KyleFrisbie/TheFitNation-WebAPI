package com.thefitnation.web.rest;

import com.thefitnation.model.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by michael on 2/19/17.
 */
@RestController
@RequestMapping("/api/v1/gym")
public class GymResource {

    private final GymService gymService;
    private final GymRepo gymRepo;

    @Autowired
    public GymResource(GymService gymService, GymRepo gymRepo) {
        this.gymService = gymService;
        this.gymRepo = gymRepo;
    }

    @RequestMapping(name = "/", method = RequestMethod.GET)
    Iterable<Gym> getAllGyms() {
        return gymRepo.findAll();
    }

    // TODO: 2/25/17 post to create gym
    @RequestMapping(method = RequestMethod.POST)
    Gym createNewGym(@PathVariable Long gymId) {
        return gymRepo.;
    }

    // TODO: 2/25/17 update a single gym using put
    // TODO: 2/25/17 delete to delete gym


    // TODO: 2/25/17 get single gym by id
    @RequestMapping(method = RequestMethod.GET, value = "/{gymId}")
    Gym findOneGymById(@PathVariable Long gymId) {
        return gymRepo.findOne(gymId);
    }

}
