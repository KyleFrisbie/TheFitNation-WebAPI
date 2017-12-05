package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.testTools.ExerciseInstanceGenerator;
import com.thefitnation.testTools.ExerciseInstanceSetGenerator;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class ExerciseInstanceSetRepositoryIntTest {

    private static final int NUMBER_OF_EXERCISE_INSTANCE_SETS = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ExerciseInstanceSetRepository exerciseInstanceSetRepository;

    @Test
    public void findAll() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstanceSetGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCE_SETS, userDemographic);

        ExerciseInstanceSetGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCE_SETS);
        Page<ExerciseInstanceSet> exerciseInstanceSets = exerciseInstanceSetRepository.findAll(user.getLogin(), null);

        assertThat(exerciseInstanceSets.getTotalElements()).isEqualTo(NUMBER_OF_EXERCISE_INSTANCE_SETS);
    }

    @Test
    public void findOne() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstanceSet exerciseInstanceSet = ExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstanceSet);
        entityManager.flush();

        ExerciseInstanceSet savedExerciseInstanceSet = exerciseInstanceSetRepository.findOne(user.getLogin(), exerciseInstanceSet.getId());

        assertThat(savedExerciseInstanceSet.getId()).isEqualTo(exerciseInstanceSet.getId());
        assertThat(savedExerciseInstanceSet.getExerciseInstance().getWorkoutInstance().getWorkoutTemplate().getUserDemographic().getUser().getId()).isEqualTo(user.getId());
    }
}
