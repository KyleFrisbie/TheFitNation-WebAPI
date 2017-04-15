package com.thefitnation.testTools;

import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.domain.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylel on 4/15/2017.
 */
public class ExerciseInstanceSetGenerator implements IOwnedEntityGenerator<ExerciseInstanceSet> {

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Float DEFAULT_REQ_QUANTITY = 1F;
    private static final Float DEFAULT_EFFORT_QUANTITY = 1F;
    private static final Float DEFAULT_REST_TIME = 1F;
    private static final String DEFAULT_NOTES = "AAAAAAAAAA";

    private static ExerciseInstanceSetGenerator instance;

    private ExerciseInstanceSetGenerator() {}

    public static ExerciseInstanceSetGenerator getInstance() {
        if (instance == null) {
            instance = new ExerciseInstanceSetGenerator();
        }
        return instance;
    }

    private ExerciseInstanceSet createExerciseInstanceSet(ExerciseInstance exerciseInstance) {
        return new ExerciseInstanceSet()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .reqQuantity(DEFAULT_REQ_QUANTITY)
            .restTime(DEFAULT_REST_TIME)
            .effortQuantity(DEFAULT_EFFORT_QUANTITY)
            .notes(DEFAULT_NOTES)
            .exerciseInstance(exerciseInstance);
    }

    @Override
    public ExerciseInstanceSet getOne(EntityManager entityManager) {
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager);
        entityManager.persist(exerciseInstance);
        entityManager.flush();

        return createExerciseInstanceSet(exerciseInstance);
    }

    @Override
    public ExerciseInstanceSet getOne(EntityManager entityManager, User user) {
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager, user);
        entityManager.persist(exerciseInstance);
        entityManager.flush();

        return createExerciseInstanceSet(exerciseInstance);
    }

    @Override
    public ExerciseInstanceSet getOne(EntityManager entityManager, String username, String password) {
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager, username, password);
        entityManager.persist(exerciseInstance);
        entityManager.flush();

        return createExerciseInstanceSet(exerciseInstance);
    }

    @Override
    public List<ExerciseInstanceSet> getMany(EntityManager entityManager, int count) {
        List<ExerciseInstanceSet> exerciseInstanceSets  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ExerciseInstanceSet exerciseInstanceSet = getOne(entityManager);
            entityManager.persist(exerciseInstanceSet);
            entityManager.flush();
            exerciseInstanceSets.add(exerciseInstanceSet);
        }
        return exerciseInstanceSets;
    }

    @Override
    public List<ExerciseInstanceSet> getMany(EntityManager entityManager, int count, User user) {
        List<ExerciseInstanceSet> exerciseInstanceSets  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ExerciseInstanceSet exerciseInstanceSet = getOne(entityManager, user);
            entityManager.persist(exerciseInstanceSet);
            entityManager.flush();
            exerciseInstanceSets.add(exerciseInstanceSet);
        }
        return exerciseInstanceSets;
    }

    @Override
    public List<ExerciseInstanceSet> getMany(EntityManager entityManager, int count, String username, String password) {
        List<ExerciseInstanceSet> exerciseInstanceSets  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ExerciseInstanceSet exerciseInstanceSet = getOne(entityManager, username, password);
            entityManager.persist(exerciseInstanceSet);
            entityManager.flush();
            exerciseInstanceSets.add(exerciseInstanceSet);
        }
        return exerciseInstanceSets;
    }
}
