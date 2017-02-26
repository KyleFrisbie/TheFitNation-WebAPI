package com.thefitnation.service;

import com.thefitnation.repository.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by michael on 2/19/17.
 */
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserRepo userRepo;
    private final UserCredentialRepo userDemographicDao;
    private final UserWeightRepo userWeightRepo;

    @Autowired
    public UserService(UserRepo userRepo, UserCredentialRepo userDemographicDao,
                       UserWeightRepo userWeightRepo) {
        this.userRepo = userRepo;
        this.userDemographicDao = userDemographicDao;
        this.userWeightRepo = userWeightRepo;
    }



}
