package com.thefitnation.service;

import com.thefitnation.model.User;
import com.thefitnation.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

/**
 * Created by michael on 2/19/17.
 */
@Service
public class UserService {

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

    public ResponseEntity<Iterable<User>> getAllUsers() {
        return new ResponseEntity<Iterable<User>>(userRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<User> createUser(User user) {
        if (null != user) {
            if(null == user.getUserId()) {
                user.setCreateDate(LocalDate.now());
                user.setLastLogin(LocalDate.MAX);
                user.setActive(false);
                try {
                    userRepo.save(user);
                    return new ResponseEntity<User>(user, HttpStatus.CREATED);
                } catch (ConstraintViolationException e) {
                    return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            else {
                return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<User>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
