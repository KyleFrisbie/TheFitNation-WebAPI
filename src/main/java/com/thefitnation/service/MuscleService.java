package com.thefitnation.service;

import com.thefitnation.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by michael on 2/24/17.
 */
@Service
public class MuscleService {

    private final MuscleRepo muscleRepo;

    @Autowired
    public MuscleService(MuscleRepo muscleRepo) {
        this.muscleRepo = muscleRepo;
    }
}
