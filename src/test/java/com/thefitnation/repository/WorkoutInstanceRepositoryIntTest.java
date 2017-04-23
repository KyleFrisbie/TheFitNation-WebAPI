package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserGenerator;
import com.thefitnation.testTools.WorkoutInstanceGenerator;
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
public class WorkoutInstanceRepositoryIntTest {

    private static final int NUMBER_OF_WORKOUT_INSTANCES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private WorkoutInstanceRepository workoutInstanceRepository;

    @Test
    public void findAll() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_INSTANCES, userDemographic);

        WorkoutInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_INSTANCES);
        Page<WorkoutInstance> workoutInstances = workoutInstanceRepository.findAll(user.getLogin(), null);

        assertThat(workoutInstances.getTotalElements()).isEqualTo(NUMBER_OF_WORKOUT_INSTANCES);
    }

    @Test
    public void findOne() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(workoutInstance);
        entityManager.flush();

        WorkoutInstance savedWorkoutInstance = workoutInstanceRepository.findOne(user.getLogin(), workoutInstance.getId());

        assertThat(savedWorkoutInstance.getId()).isEqualTo(workoutInstance.getId());
        assertThat(savedWorkoutInstance.getWorkoutTemplate().getUserDemographic().getUser().getId()).isEqualTo(user.getId());
    }
}
