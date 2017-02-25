package com.thefitnation.web.rest;

import com.thefitnation.model.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.*;
import java.net.*;
import javax.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by michael on 2/19/17.
 */
@RestController
@RequestMapping("/api/v1/gym")
public class GymResource {

    private final GymService gymService;
    private final GymRepo gymRepo;
    private final LocationRepo locationRepo;
    private final AddressRepo addressRepo;

    @Autowired
    public GymResource(GymService gymService, GymRepo gymRepo, LocationRepo locationRepo, AddressRepo addressRepo) {
        this.gymService = gymService;
        this.gymRepo = gymRepo;
        this.locationRepo = locationRepo;
        this.addressRepo = addressRepo;
    }

    @RequestMapping(name = "/", method = RequestMethod.GET)
    Iterable<Gym> getAllGyms() {
        return gymRepo.findAll();
    }

    /**
     * POST  /gyms : Create a new gym.
     *
     * @param gym the gym to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gym, or with status 400 (Bad Request) if the gym has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<Gym> createGym(@Valid @RequestBody Gym gym) throws URISyntaxException {
//        log.debug("REST request to save Gym : {}", gym);
        if (gym.getGymId() != null) {
            return new ResponseEntity<Gym>(gym, HttpStatus.I_AM_A_TEAPOT);
        }

        Location location = gym.getGymLocation();
        Address a = addressRepo.save(location.getAddress());

        location.setAddress(a);

        Location l = locationRepo.save(gym.getGymLocation());



        gym.setGymLocation(l);

        Gym result = gymRepo.save(gym);
        return new ResponseEntity<Gym>(result, HttpStatus.OK);
    }




    // TODO: 2/25/17 update a single gym using put
    // TODO: 2/25/17 delete to delete gym


    // TODO: 2/25/17 get single gym by id
    @RequestMapping(method = RequestMethod.GET, value = "/{gymId}")
    ResponseEntity<Gym> findOneGymById(@PathVariable Long gymId) {
        return new ResponseEntity<Gym>(gymRepo.findOne(gymId), HttpStatus.OK);
    }

}
