package com.thefitnation.testTools;

import com.thefitnation.domain.User;
import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.domain.WorkoutTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class WorkoutInstanceGenerator implements IOwnedEntityGenerator<WorkoutInstance> {

    private static WorkoutInstanceGenerator instance;

    private WorkoutInstanceGenerator() {}

    public static WorkoutInstanceGenerator getInstance() {
        if (instance == null) {
            instance = new WorkoutInstanceGenerator();
        }
        return instance;
    }

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate DEFAULT_LAST_UPDATED = LocalDate.ofEpochDay(0L);
    private static final Float DEFAULT_REST_BETWEEN_INSTANCES = 1F;
    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final String DEFAULT_NOTES = "AAAAAAAAAA";

    private WorkoutInstance createWorkoutInstance(WorkoutTemplate workoutTemplate) {
        return new WorkoutInstance()
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .name(DEFAULT_NAME)
            .restBetweenInstances(DEFAULT_REST_BETWEEN_INSTANCES)
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .notes(DEFAULT_NOTES)
            .workoutTemplate(workoutTemplate);
    }

    @Override
    public WorkoutInstance getOne(EntityManager entityManager) {
        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager);
        entityManager.persist(workoutTemplate);
        entityManager.flush();

        return createWorkoutInstance(workoutTemplate);
    }

    @Override
    public WorkoutInstance getOne(EntityManager entityManager, User user) {
        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, user);
        entityManager.persist(workoutTemplate);
        entityManager.flush();

        return createWorkoutInstance(workoutTemplate);
    }

    @Override
    public WorkoutInstance getOne(EntityManager entityManager, String username, String password) {
        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, username, password);
        entityManager.persist(workoutTemplate);
        entityManager.flush();

        return createWorkoutInstance(workoutTemplate);
    }

    @Override
    public List<WorkoutInstance> getMany(EntityManager entityManager, int count) {
        List<WorkoutInstance> workoutInstances = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            WorkoutInstance workoutInstance = getOne(entityManager);
            entityManager.persist(workoutInstance);
            entityManager.flush();
            workoutInstances.add(workoutInstance);
        }
        return workoutInstances;
    }

    @Override
    public List<WorkoutInstance> getMany(EntityManager entityManager, int count, User user) {
        List<WorkoutInstance> workoutInstances = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            WorkoutInstance workoutInstance = getOne(entityManager, user);
            entityManager.persist(workoutInstance);
            entityManager.flush();
            workoutInstances.add(workoutInstance);
        }
        return workoutInstances;
    }

    @Override
    public List<WorkoutInstance> getMany(EntityManager entityManager, int count, String username, String password) {
        List<WorkoutInstance> workoutInstances = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            WorkoutInstance workoutInstance = getOne(entityManager, username, password);
            entityManager.persist(workoutInstance);
            entityManager.flush();
            workoutInstances.add(workoutInstance);
        }
        return workoutInstances;
    }
}
