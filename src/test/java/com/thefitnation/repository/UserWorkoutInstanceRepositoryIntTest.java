package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWorkoutInstance;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserGenerator;
import com.thefitnation.testTools.UserWorkoutInstanceGenerator;
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
public class UserWorkoutInstanceRepositoryIntTest {

    private static final int NUMBER_OF_USER_WORKOUT_INSTANCES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserWorkoutInstanceRepository userWorkoutInstanceRepository;

    @Test
    public void findAll() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_USER_WORKOUT_INSTANCES, userDemographic);

        UserWorkoutInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_USER_WORKOUT_INSTANCES);
        Page<UserWorkoutInstance> userWorkoutInstance = userWorkoutInstanceRepository.findAll(user.getLogin(), null);

        assertThat(userWorkoutInstance.getTotalElements()).isEqualTo(NUMBER_OF_USER_WORKOUT_INSTANCES);
    }

    @Test
    public void findOne() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutInstance);
        entityManager.flush();

        UserWorkoutInstance savedUserWorkoutInstance = userWorkoutInstanceRepository.findOne(user.getLogin(), userWorkoutInstance.getId());

        assertThat(savedUserWorkoutInstance.getId()).isEqualTo(userWorkoutInstance.getId());
        assertThat(savedUserWorkoutInstance.getUserWorkoutTemplate().getUserDemographic().getUser().getId()).isEqualTo(user.getId());
    }
}
