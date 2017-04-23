package com.thefitnation.testTools;

import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWorkoutTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserWorkoutTemplateGenerator implements IOwnedEntityGenerator<UserWorkoutTemplate> {

    private static UserWorkoutTemplateGenerator instance;

    private UserWorkoutTemplateGenerator() {}

    public static UserWorkoutTemplateGenerator getInstance() {
        if (instance == null) {
            instance = new UserWorkoutTemplateGenerator();
        }
        return instance;
    }

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate DEFAULT_LAST_UPDATED = LocalDate.ofEpochDay(0L);
    private static final String DEFAULT_NOTES = "AAAAAAAAAA";

    private UserWorkoutTemplate getUserWorkoutTemplate(UserDemographic userDemographic) {
        return new UserWorkoutTemplate()
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .notes(DEFAULT_NOTES)
            .userDemographic(userDemographic);
    }

    @Override
    public UserWorkoutTemplate getOne(EntityManager entityManager) {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        return getUserWorkoutTemplate(userDemographic);
    }

    @Override
    public UserWorkoutTemplate getOne(EntityManager entityManager,  UserDemographic userDemographic) {
        return getUserWorkoutTemplate(userDemographic);
    }

    @Override
    public List<UserWorkoutTemplate> getMany(EntityManager entityManager, int count) {
        List<UserWorkoutTemplate> workoutTemplates = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserWorkoutTemplate userWorkoutTemplate = getOne(entityManager);
            entityManager.persist(userWorkoutTemplate);
            entityManager.flush();
            workoutTemplates.add(userWorkoutTemplate);
        }
        return workoutTemplates;
    }

    @Override
    public List<UserWorkoutTemplate> getMany(EntityManager entityManager, int count,  UserDemographic userDemographic) {
        List<UserWorkoutTemplate> workoutTemplates = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserWorkoutTemplate userWorkoutTemplate = getOne(entityManager, userDemographic);
            entityManager.persist(userWorkoutTemplate);
            entityManager.flush();
            workoutTemplates.add(userWorkoutTemplate);
        }
        return workoutTemplates;
    }
}
