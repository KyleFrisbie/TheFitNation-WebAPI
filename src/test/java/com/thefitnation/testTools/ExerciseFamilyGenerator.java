package com.thefitnation.testTools;

import com.thefitnation.domain.ExerciseFamily;

import javax.persistence.EntityManager;

/**
 * Created by kylel on 4/15/2017.
 */
public class ExerciseFamilyGenerator implements IUnownedEntityGenerator<ExerciseFamily> {

    private static int NAME_COUNTER = 0;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";

    private static ExerciseFamilyGenerator instance;

    private ExerciseFamilyGenerator() {}

    public static ExerciseFamilyGenerator getInstance() {
        if(instance == null) {
            instance = new ExerciseFamilyGenerator();
        }
        return instance;
    }

    @Override
    public ExerciseFamily getOne(EntityManager entityManager) {
        return new ExerciseFamily()
            .name(DEFAULT_NAME + NAME_COUNTER++);
    }
}
