package com.thefitnation.testTools;

import com.thefitnation.domain.Exercise;
import com.thefitnation.domain.ExerciseFamily;
import com.thefitnation.domain.Muscle;
import com.thefitnation.domain.SkillLevel;

import javax.persistence.EntityManager;
import java.util.HashSet;

/**
 * Created by kylel on 4/15/2017.
 */
public class ExerciseGenerator implements IUnownedEntityGenerator<Exercise> {

    private static ExerciseGenerator instance;

    private ExerciseGenerator() {}

    public static ExerciseGenerator getInstance() {
        if (instance == null) {
            instance = new ExerciseGenerator();
        }
        return instance;
    }

    private static int NAME_COUNTER = 0;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String DEFAULT_IMAGE_URI = "AAAAAAAAAA";
    private static final String DEFAULT_NOTES = "AAAAAAAAAA";

    @Override
    public Exercise getOne(EntityManager entityManager) {
        SkillLevel skillLevel = SkillLevelGenerator.getInstance().getOne(entityManager);
        entityManager.persist(skillLevel);
        entityManager.flush();

        Muscle muscle = MuscleGenerator.getInstance().getOne(entityManager);
        entityManager.persist(muscle);
        entityManager.flush();

        HashSet<Muscle> muscles = new HashSet<>(1);
        muscles.add(muscle);

        ExerciseFamily exerciseFamily = ExerciseFamilyGenerator.getInstance().getOne(entityManager);
        entityManager.persist(exerciseFamily);
        entityManager.flush();

        return new Exercise()
            .name(DEFAULT_NAME + NAME_COUNTER++)
            .imageUri(DEFAULT_IMAGE_URI)
            .notes(DEFAULT_NOTES)
            .skillLevel(skillLevel)
            .muscles(muscles)
            .exerciseFamily(exerciseFamily);
    }
}
