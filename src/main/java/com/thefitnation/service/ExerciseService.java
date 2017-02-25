package com.thefitnation.service;

import com.thefitnation.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by michael on 2/24/17.
 */
@Service
public class ExerciseService {

    private final PrescribedExerciseRepo prescribedExerciseRepo;

    @Autowired
    public ExerciseService(PrescribedExerciseRepo prescribedExerciseRepo) {
        this.prescribedExerciseRepo = prescribedExerciseRepo;
    }
}
