package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserGenerator;
import com.thefitnation.testTools.UserWorkoutTemplateGenerator;
import com.thefitnation.testTools.WorkoutTemplateGenerator;
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
public class UserWorkoutTemplateRepositoryIntTest {

    private static final int NUMBER_OF_WORKOUT_TEMPLATES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserWorkoutTemplateRepository userWorkoutTemplateRepository;

    @Test
    public void findAll() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutTemplateGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_TEMPLATES, userDemographic);

        UserWorkoutTemplateGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_TEMPLATES);
        Page<UserWorkoutTemplate> savedUserWorkoutTemplate = userWorkoutTemplateRepository.findAll(user.getLogin(), null);

        assertThat(savedUserWorkoutTemplate.getTotalElements()).isEqualTo(NUMBER_OF_WORKOUT_TEMPLATES);
    }

    @Test
    public void findOne() {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutTemplate);
        entityManager.flush();

        UserWorkoutTemplate savedUserWorkoutTemplate = userWorkoutTemplateRepository.findOne(user.getLogin(), userWorkoutTemplate.getId());

        assertThat(savedUserWorkoutTemplate.getId()).isEqualTo(userWorkoutTemplate.getId());
        assertThat(savedUserWorkoutTemplate.getUserDemographic().getUser().getId()).isEqualTo(user.getId());
    }
}
