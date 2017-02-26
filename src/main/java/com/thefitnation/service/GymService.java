package com.thefitnation.service;

import com.thefitnation.model.*;
import com.thefitnation.repository.*;
import javax.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by michael on 2/24/17.
 */
@Service
public class GymService {

    private final GymRepo gymRepo;
    private final LocationRepo locationRepo;
    private final AddressRepo addressRepo;

    @Autowired
    public GymService(GymRepo gymRepo, LocationRepo locationRepo, AddressRepo addressRepo) {
        this.gymRepo = gymRepo;
        this.locationRepo = locationRepo;
        this.addressRepo = addressRepo;
    }


    public Gym findGyByIdForUser(Long id) {
        return gymRepo.findOne(id);
    }

    public Iterable<Gym> getAllGymsForUser() {
        return gymRepo.findAll();
    }

    public ResponseEntity<Gym> createGymWithLocaitonAndAddress(@Valid @RequestBody Gym gym) {
        if (gym.getGymId() != null) {
            return new ResponseEntity<>(gym, HttpStatus.I_AM_A_TEAPOT);
        }

        Location location = gym.getGymLocation();
        Address a = addressRepo.save(location.getAddress());

        location.setAddress(a);

        Location l = locationRepo.save(gym.getGymLocation());


        gym.setGymLocation(l);

        Gym result = gymRepo.save(gym);
        return new ResponseEntity<Gym>(result, HttpStatus.OK);
    }
}
