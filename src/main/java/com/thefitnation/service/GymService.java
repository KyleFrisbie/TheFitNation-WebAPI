package com.thefitnation.service;

import com.thefitnation.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

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

}
