package com.thefitnation.testTools;

import com.thefitnation.domain.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylel on 4/15/2017.
 */
public class UserExerciseInstanceGenerator implements IOwnedEntityGenerator<UserExerciseInstance> {

    private static UserExerciseInstanceGenerator instance;

    private UserExerciseInstanceGenerator() {}

    public static UserExerciseInstanceGenerator getInstance() {
        if (instance == null) {
            instance = new UserExerciseInstanceGenerator();
        }
        return instance;
    }

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";

    private UserExerciseInstance createExerciseInstance(EntityManager entityManager, UserWorkoutInstance userWorkoutInstance) {
        Exercise exercise = ExerciseGenerator.getInstance().getOne(entityManager);
        entityManager.persist(exercise);
        entityManager.flush();

        Unit unit = UnitGenerator.getInstance().getOne(entityManager);
        entityManager.persist(unit);
        entityManager.flush();

        return new UserExerciseInstance()
            .userWorkoutInstance(userWorkoutInstance)
            .notes(DEFAULT_NOTES);
    }

    @Override
    public UserExerciseInstance getOne(EntityManager entityManager) {
        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager);
        entityManager.persist(userWorkoutInstance);
        entityManager.flush();

        return createExerciseInstance(entityManager, userWorkoutInstance);
    }

    @Override
    public UserExerciseInstance getOne(EntityManager entityManager, UserDemographic userDemographic) {
        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutInstance);
        entityManager.flush();

        return createExerciseInstance(entityManager, userWorkoutInstance);
    }

    @Override
    public List<UserExerciseInstance> getMany(EntityManager entityManager, int count) {
        List<UserExerciseInstance> exerciseInstances  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserExerciseInstance exerciseInstance = getOne(entityManager);
            entityManager.persist(exerciseInstance);
            entityManager.flush();
            exerciseInstances.add(exerciseInstance);
        }
        return exerciseInstances;
    }

    @Override
    public List<UserExerciseInstance> getMany(EntityManager entityManager, int count, UserDemographic userDemographic) {
        List<UserExerciseInstance> exerciseInstances  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserExerciseInstance exerciseInstance = getOne(entityManager, userDemographic);
            entityManager.persist(exerciseInstance);
            entityManager.flush();
            exerciseInstances.add(exerciseInstance);
        }
        return exerciseInstances;
    }
}
