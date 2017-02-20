package com.thefitnation.service;

import com.thefitnation.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by michael on 2/19/17.
 */
@Service
public class UserService {

    private final UserDao userDao;
    private final UserDemographicDao userDemographicDao;


    @Autowired
    public UserService(UserDao userDao, UserDemographicDao userDemographicDao) {
        this.userDao = userDao;
        this.userDemographicDao = userDemographicDao;
    }

}
