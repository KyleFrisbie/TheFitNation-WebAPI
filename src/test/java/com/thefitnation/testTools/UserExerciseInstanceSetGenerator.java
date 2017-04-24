package com.thefitnation.testTools;

import com.thefitnation.domain.UserExerciseInstance;
import com.thefitnation.domain.UserExerciseInstanceSet;
import com.thefitnation.domain.UserDemographic;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylel on 4/15/2017.
 */
public class UserExerciseInstanceSetGenerator implements IOwnedEntityGenerator<UserExerciseInstanceSet> {

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Float DEFAULT_REQ_QUANTITY = 1F;
    private static final Float DEFAULT_EFFORT_QUANTITY = 1F;
    private static final Float DEFAULT_REST_TIME = 1F;
    private static final String DEFAULT_NOTES = "AAAAAAAAAA";

    private static UserExerciseInstanceSetGenerator instance;

    private UserExerciseInstanceSetGenerator() {}

    public static UserExerciseInstanceSetGenerator getInstance() {
        if (instance == null) {
            instance = new UserExerciseInstanceSetGenerator();
        }
        return instance;
    }

    private UserExerciseInstanceSet createExerciseInstanceSet(UserExerciseInstance userExerciseInstance) {
        return new UserExerciseInstanceSet()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .repQuantity(DEFAULT_REQ_QUANTITY)
            .restTime(DEFAULT_REST_TIME)
            .effortQuantity(DEFAULT_EFFORT_QUANTITY)
            .notes(DEFAULT_NOTES)
            .userExerciseInstance(userExerciseInstance);
    }

    @Override
    public UserExerciseInstanceSet getOne(EntityManager entityManager) {
        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(entityManager);
        entityManager.persist(userExerciseInstance);
        entityManager.flush();

        return createExerciseInstanceSet(userExerciseInstance);
    }

    @Override
    public UserExerciseInstanceSet getOne(EntityManager entityManager, UserDemographic userDemographic) {
        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstance);
        entityManager.flush();

        return createExerciseInstanceSet(userExerciseInstance);
    }

    @Override
    public List<UserExerciseInstanceSet> getMany(EntityManager entityManager, int count) {
        List<UserExerciseInstanceSet> exerciseInstanceSets  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserExerciseInstanceSet exerciseInstanceSet = getOne(entityManager);
            entityManager.persist(exerciseInstanceSet);
            entityManager.flush();
            exerciseInstanceSets.add(exerciseInstanceSet);
        }
        return exerciseInstanceSets;
    }

    @Override
    public List<UserExerciseInstanceSet> getMany(EntityManager entityManager, int count, UserDemographic userDemographic) {
        List<UserExerciseInstanceSet> exerciseInstanceSets  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserExerciseInstanceSet exerciseInstanceSet = getOne(entityManager, userDemographic);
            entityManager.persist(exerciseInstanceSet);
            entityManager.flush();
            exerciseInstanceSets.add(exerciseInstanceSet);
        }
        return exerciseInstanceSets;
    }
}
