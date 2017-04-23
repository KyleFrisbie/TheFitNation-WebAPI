package com.thefitnation.testTools;

import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWorkoutInstance;
import com.thefitnation.domain.UserWorkoutTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserWorkoutInstanceGenerator implements IOwnedEntityGenerator<UserWorkoutInstance> {

    private static UserWorkoutInstanceGenerator instance;

    private UserWorkoutInstanceGenerator() {}

    public static UserWorkoutInstanceGenerator getInstance() {
        if (instance == null) {
            instance = new UserWorkoutInstanceGenerator();
        }
        return instance;
    }

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate DEFAULT_LAST_UPDATED = LocalDate.ofEpochDay(0L);
    private static final boolean DEFAULT_WAS_COMPLETED = false;
    private static final String DEFAULT_NOTES = "AAAAAAAAAA";

    private UserWorkoutInstance createUserWorkoutInstance(UserWorkoutTemplate userWorkoutTemplate) {
        return new UserWorkoutInstance()
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .wasCompleted(DEFAULT_WAS_COMPLETED)
            .notes(DEFAULT_NOTES)
            .userWorkoutTemplate(userWorkoutTemplate);
    }

    @Override
    public UserWorkoutInstance getOne(EntityManager entityManager) {
        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager);
        entityManager.persist(userWorkoutTemplate);
        entityManager.flush();

        return createUserWorkoutInstance(userWorkoutTemplate);
    }

    @Override
    public UserWorkoutInstance getOne(EntityManager entityManager, UserDemographic userDemographic) {
        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutTemplate);
        entityManager.flush();

        return createUserWorkoutInstance(userWorkoutTemplate);
    }

    @Override
    public List<UserWorkoutInstance> getMany(EntityManager entityManager, int count) {
        List<UserWorkoutInstance> workoutInstances = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserWorkoutInstance userWorkoutInstance = getOne(entityManager);
            entityManager.persist(userWorkoutInstance);
            entityManager.flush();
            workoutInstances.add(userWorkoutInstance);
        }
        return workoutInstances;
    }

    @Override
    public List<UserWorkoutInstance> getMany(EntityManager entityManager, int count, UserDemographic userDemographic) {
        List<UserWorkoutInstance> workoutInstances = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserWorkoutInstance userWorkoutInstance = getOne(entityManager, userDemographic);
            entityManager.persist(userWorkoutInstance);
            entityManager.flush();
            workoutInstances.add(userWorkoutInstance);
        }
        return workoutInstances;
    }
}
