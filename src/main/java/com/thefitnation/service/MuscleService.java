package com.thefitnation.service;

import com.thefitnation.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by michael on 2/21/17.
 */
@Service
public class MuscleService {


    private final MuscleDao muscleDao;


    @Autowired
    public MuscleService(MuscleDao muscleDao) {
        this.muscleDao = muscleDao;
    }
}
