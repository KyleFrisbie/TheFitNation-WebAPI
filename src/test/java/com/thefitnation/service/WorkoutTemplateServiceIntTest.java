package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.WorkoutTemplate;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.WorkoutTemplateRepository;
import com.thefitnation.service.dto.WorkoutTemplateDTO;
import com.thefitnation.service.mapper.WorkoutTemplateMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.WorkoutTemplateGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class WorkoutTemplateServiceIntTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private WorkoutTemplateService workoutTemplateService;

    @Autowired
    private WorkoutTemplateRepository workoutTemplateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutTemplateMapper workoutTemplateMapper;

    @Test
    public void saveWorkoutTemplateAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);

        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        WorkoutTemplateDTO testUserDemographicDTO = workoutTemplateService.save(workoutTemplateDTO);

        int databaseSizeAfterCreate = workoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
        assertThat(testUserDemographicDTO.getId()).isNotNull();
        assertThat(testUserDemographicDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
    }

    @Test
    public void saveWorkoutTemplateWithoutLoggedInUser() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);

        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        WorkoutTemplateDTO testUserDemographicDTO = workoutTemplateService.save(workoutTemplateDTO);

        int databaseSizeAfterCreate = workoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate);
        assertThat(testUserDemographicDTO).isNull();
    }

    @Test
    public void saveWorkoutTemplateWithoutUserDemographicAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);

        workoutTemplate.setUserDemographic(null);

        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        WorkoutTemplateDTO testUserDemographicDTO = workoutTemplateService.save(workoutTemplateDTO);

        int databaseSizeAfterCreate = workoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
        assertThat(testUserDemographicDTO.getId()).isNotNull();
        assertThat(testUserDemographicDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
    }
}
