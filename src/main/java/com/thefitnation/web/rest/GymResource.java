package com.thefitnation.web.rest;

import com.thefitnation.model.*;
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


    @Autowired
    public GymResource(GymService gymService) {
        this.gymService = gymService;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<Gym> findGym(@PathVariable Long id) {
        return new ResponseEntity<>(gymService.findGyByIdForUser(id), HttpStatus.OK);
    }

    @RequestMapping(name = "/", method = RequestMethod.GET)
    Iterable<Gym> getAllGyms() {
        return gymService.getAllGymsForUser();
    }

    /**
     *<p></p>
     * @param gym the gym to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gym, or with status 400 (Bad Request) if the gym has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<Gym> createGym(@Valid @RequestBody Gym gym) throws URISyntaxException {
//        log.debug("REST request to save Gym : {}", gym);
        return gymService.createGymWithLocaitonAndAddress(gym);
    }



    // TODO: 2/25/17 update a single gym using put
    // TODO: 2/25/17 delete to delete gym

}
