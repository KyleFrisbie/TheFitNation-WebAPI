package com.thefitnation.service;

import com.thefitnation.model.*;
import com.thefitnation.model.enumeration.*;
import com.thefitnation.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.stereotype.*;

/**
 * Created by michael on 2/24/17.
 */
@Service
public class TestDataLoader implements CommandLineRunner {


    private final MuscleRepo muscleRepo;

    @Autowired
    public TestDataLoader(MuscleRepo muscleRepo) {
        this.muscleRepo = muscleRepo;
    }


    @Override
    public void run(String... strings) throws Exception {

        Muscle m = new Muscle("platysma", BodyPart.Neck);
        muscleRepo.save(m);
//        'sternocleidomastoid', 'neck'
//        'digastric', 'neck'
//        'stylohyoid', 'neck'
//        'mylohyoid', 'neck'
//        'geniohyoid', 'neck'
//        'sternohyoid', 'neck'
//        'sternothyroid', 'neck'
//        'thyrohyoid', 'neck'
//        'omohyoid', 'neck'
//        'scalene muscles', 'neck'
//        'anterior', 'neck'
//        'medius', 'neck'
//        'posterior', 'neck'
//        'levator scapulae', 'neck'
//        'rectus capitis lateralis', 'neck'
//        'obliquus capitis superior', 'neck'
//        'rectus capitis posterior minor', 'neck'
//        'rectus capitis posterior major', 'neck'
//        'semispinalis capitis', 'neck'
//        'longissimus capitis', 'neck'
//        'splenius capitis', 'neck'
    }
}
