package com.thefitnation.testTools;

import com.thefitnation.domain.SkillLevel;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.WorkoutTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutTemplateGenerator implements IOwnedEntityGenerator<WorkoutTemplate> {

    private static WorkoutTemplateGenerator instance;

    private WorkoutTemplateGenerator() {}

    public static WorkoutTemplateGenerator getInstance() {
        if (instance == null) {
            instance = new WorkoutTemplateGenerator();
        }
        return instance;
    }

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate DEFAULT_LAST_UPDATED = LocalDate.ofEpochDay(0L);
    private static final Boolean DEFAULT_IS_PRIVATE = false;
    private static final String DEFAULT_NOTES = "AAAAAAAAAA";

    private SkillLevel getSkillLevel(EntityManager entityManager) {
        SkillLevel skillLevel = SkillLevelGenerator.getInstance().getOne(entityManager);
        entityManager.persist(skillLevel);
        entityManager.flush();

        return skillLevel;
    }

    private WorkoutTemplate getWorkoutTemplate(EntityManager entityManager, UserDemographic userDemographic) {
        return new WorkoutTemplate()
            .name(DEFAULT_NAME)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .isPrivate(DEFAULT_IS_PRIVATE)
            .notes(DEFAULT_NOTES)
            .skillLevel(getSkillLevel(entityManager))
            .userDemographic(userDemographic);
    }

    @Override
    public WorkoutTemplate getOne(EntityManager entityManager) {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        return getWorkoutTemplate(entityManager, userDemographic);
    }

    @Override
    public WorkoutTemplate getOne(EntityManager entityManager,  UserDemographic userDemographic) {
        return getWorkoutTemplate(entityManager, userDemographic);
    }

    @Override
    public List<WorkoutTemplate> getMany(EntityManager entityManager, int count) {
        List<WorkoutTemplate> workoutTemplates = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            WorkoutTemplate workoutTemplate = getOne(entityManager);
            entityManager.persist(workoutTemplate);
            entityManager.flush();
            workoutTemplates.add(workoutTemplate);
        }
        return workoutTemplates;
    }

    @Override
    public List<WorkoutTemplate> getMany(EntityManager entityManager, int count,  UserDemographic userDemographic) {
        List<WorkoutTemplate> workoutTemplates = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            WorkoutTemplate workoutTemplate = getOne(entityManager, userDemographic);
            entityManager.persist(workoutTemplate);
            entityManager.flush();
            workoutTemplates.add(workoutTemplate);
        }
        return workoutTemplates;
    }
}
