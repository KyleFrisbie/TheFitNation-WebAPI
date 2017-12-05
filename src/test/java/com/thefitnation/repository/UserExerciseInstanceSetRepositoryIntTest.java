package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.UserExerciseInstanceSet;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.testTools.UserExerciseInstanceSetGenerator;
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
public class UserExerciseInstanceSetRepositoryIntTest {

    private static final int NUMBER_OF_EXERCISE_INSTANCE_SETS = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;

    @Test
    public void findAll() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstanceSetGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCE_SETS, userDemographic);

        UserExerciseInstanceSetGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCE_SETS);
        Page<UserExerciseInstanceSet> userExerciseInstanceSets = userExerciseInstanceSetRepository.findAll(user.getLogin(), null);

        assertThat(userExerciseInstanceSets.getTotalElements()).isEqualTo(NUMBER_OF_EXERCISE_INSTANCE_SETS);
    }

    @Test
    public void findOne() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstanceSet userExerciseInstanceSet = UserExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstanceSet);
        entityManager.flush();

        UserExerciseInstanceSet savedExerciseInstanceSet = userExerciseInstanceSetRepository.findOne(user.getLogin(), userExerciseInstanceSet.getId());

        assertThat(savedExerciseInstanceSet.getId()).isEqualTo(userExerciseInstanceSet.getId());
        assertThat(savedExerciseInstanceSet.getUserExerciseInstance().getUserWorkoutInstance().getUserWorkoutTemplate().getUserDemographic().getUser().getId()).isEqualTo(user.getId());
    }
}
