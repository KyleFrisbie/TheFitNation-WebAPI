package com.thefitnation.testTools;

import com.thefitnation.domain.BodyPart;
import com.thefitnation.domain.Muscle;

import javax.persistence.EntityManager;

/**
 * Created by kylel on 4/15/2017.
 */
public class MuscleGenerator implements IUnownedEntityGenerator<Muscle> {

    private static int NAME_COUNTER = 0;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";

    private static MuscleGenerator instance;

    private MuscleGenerator() {}

    public static MuscleGenerator getInstance() {
        if(instance == null) {
            instance = new MuscleGenerator();
        }
        return instance;
    }

    @Override
    public Muscle getOne(EntityManager entityManager) {
        BodyPart bodyPart = BodyPartGenerator.getInstance().getOne(entityManager);
        entityManager.persist(bodyPart);
        entityManager.flush();

        return new Muscle()
            .name(DEFAULT_NAME + NAME_COUNTER++)
            .bodyPart(bodyPart);
    }
}
