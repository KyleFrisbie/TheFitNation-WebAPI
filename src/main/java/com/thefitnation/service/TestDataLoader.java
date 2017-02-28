package com.thefitnation.service;

import com.thefitnation.model.*;
import com.thefitnation.repository.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by michael on 2/24/17.
 */
@Service
@Profile("dev")
public class TestDataLoader implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MuscleRepo muscleRepo;
    private final GymRepo gymRepo;

    @Autowired
    public TestDataLoader(MuscleRepo muscleRepo, GymRepo gymRepo) {
        this.muscleRepo = muscleRepo;
        this.gymRepo = gymRepo;
    }

    @Override
    public void run(String... strings) throws Exception {

    Gym g = new Gym("FitNation");
    gymRepo.save(g);

    }
}
