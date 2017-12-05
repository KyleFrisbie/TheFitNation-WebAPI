package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.testTools.ExerciseInstanceGenerator;
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
public class ExerciseInstanceRepositoryIntTest {

    private static final int NUMBER_OF_EXERCISE_INSTANCES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ExerciseInstanceRepository exerciseInstanceRepository;

    @Test
    public void findAll() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES, userDemographic);

        ExerciseInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES);
        Page<ExerciseInstance> exerciseInstances = exerciseInstanceRepository.findAll(user.getLogin(), null);

        assertThat(exerciseInstances.getTotalElements()).isEqualTo(NUMBER_OF_EXERCISE_INSTANCES);
    }

    @Test
    public void findOne() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstance);
        entityManager.flush();

        ExerciseInstance savedExerciseInstance = exerciseInstanceRepository.findOne(user.getLogin(), exerciseInstance.getId());

        assertThat(savedExerciseInstance.getId()).isEqualTo(exerciseInstance.getId());
        assertThat(savedExerciseInstance.getWorkoutInstance().getWorkoutTemplate().getUserDemographic().getUser().getId()).isEqualTo(user.getId());
    }
}
