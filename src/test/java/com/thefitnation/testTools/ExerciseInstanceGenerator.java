package com.thefitnation.testTools;

import com.thefitnation.domain.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylel on 4/15/2017.
 */
public class ExerciseInstanceGenerator implements IOwnedEntityGenerator<ExerciseInstance> {

    private static ExerciseInstanceGenerator instance;

    private ExerciseInstanceGenerator() {}

    public static ExerciseInstanceGenerator getInstance() {
        if (instance == null) {
            instance = new ExerciseInstanceGenerator();
        }
        return instance;
    }

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";

    private ExerciseInstance createExerciseInstance(EntityManager entityManager, WorkoutInstance workoutInstance) {
        Exercise exercise = ExerciseGenerator.getInstance().getOne(entityManager);
        entityManager.persist(exercise);
        entityManager.flush();

        Unit unit = UnitGenerator.getInstance().getOne(entityManager);
        entityManager.persist(unit);
        entityManager.flush();

        return new ExerciseInstance()
            .workoutInstance(workoutInstance)
            .notes(DEFAULT_NOTES)
            .exercise(exercise)
            .repUnit(unit)
            .effortUnit(unit);
    }

    @Override
    public ExerciseInstance getOne(EntityManager entityManager) {
        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager);
        entityManager.persist(workoutInstance);
        entityManager.flush();

        return createExerciseInstance(entityManager, workoutInstance);
    }

    @Override
    public ExerciseInstance getOne(EntityManager entityManager, User user) {
        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager, user);
        entityManager.persist(workoutInstance);
        entityManager.flush();

        return createExerciseInstance(entityManager, workoutInstance);
    }

    @Override
    public ExerciseInstance getOne(EntityManager entityManager, String username, String password) {
        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager, username, password);
        entityManager.persist(workoutInstance);
        entityManager.flush();

        return createExerciseInstance(entityManager, workoutInstance);
    }

    @Override
    public List<ExerciseInstance> getMany(EntityManager entityManager, int count) {
        List<ExerciseInstance> exerciseInstances  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ExerciseInstance exerciseInstance = getOne(entityManager);
            entityManager.persist(exerciseInstance);
            entityManager.flush();
            exerciseInstances.add(exerciseInstance);
        }
        return exerciseInstances;
    }

    @Override
    public List<ExerciseInstance> getMany(EntityManager entityManager, int count, User user) {
        List<ExerciseInstance> exerciseInstances  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ExerciseInstance exerciseInstance = getOne(entityManager, user);
            entityManager.persist(exerciseInstance);
            entityManager.flush();
            exerciseInstances.add(exerciseInstance);
        }
        return exerciseInstances;
    }

    @Override
    public List<ExerciseInstance> getMany(EntityManager entityManager, int count, String username, String password) {
        List<ExerciseInstance> exerciseInstances  = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ExerciseInstance exerciseInstance = getOne(entityManager, username, password);
            entityManager.persist(exerciseInstance);
            entityManager.flush();
            exerciseInstances.add(exerciseInstance);
        }
        return exerciseInstances;
    }
}
