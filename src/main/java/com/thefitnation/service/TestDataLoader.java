package com.thefitnation.service;

import com.thefitnation.model.*;
import com.thefitnation.model.enumeration.Gender;
import com.thefitnation.model.enumeration.SkillLevel;
import com.thefitnation.model.enumeration.UnitOfMeasure;
import com.thefitnation.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.stereotype.*;

import java.time.LocalDate;

/**
 * Created by michael on 2/24/17.
 */
@Service
public class TestDataLoader implements CommandLineRunner {


    private final MuscleRepo muscleRepo;
    private final GymRepo gymRepo;
    private final UserRepo userRepo;

    @Autowired
    public TestDataLoader(MuscleRepo muscleRepo, GymRepo gymRepo, UserRepo userRepo) {
        this.muscleRepo = muscleRepo;
        this.gymRepo = gymRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... strings) throws Exception {

        Gym g = new Gym("FitNation");
        gymRepo.save(g);

        User u = new User();
        LocalDate now = LocalDate.now();
        u.setCreateDate(now);
        u.setLastLogin(now);
        u.setFirstName("Kyle");
        u.setLastName("Frisbie");
        u.setGender(Gender.Male);
        u.setDob(LocalDate.of(1989, 7, 17));
        u.setHeight(69);
        u.setSkillLevel(SkillLevel.Intermediate);
        u.setUnits(UnitOfMeasure.Imperial);
        u.setActive(true);
        userRepo.save(u);
    }
}
