package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.UserExerciseInstance;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.UserExerciseInstanceRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.service.dto.UserExerciseInstanceDTO;
import com.thefitnation.service.mapper.UserExerciseInstanceMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserExerciseInstanceGenerator;
import com.thefitnation.testTools.UserDemographicGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class UserExerciseInstanceServiceIntTest {
    private static final int NUMBER_OF_EXERCISE_INSTANCES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserExerciseInstanceService userExerciseInstanceService;

    @Autowired
    private UserExerciseInstanceRepository userExerciseInstanceRepository;

    @Autowired
    private UserExerciseInstanceMapper userExerciseInstanceMapper;

    @Test
    public void saveAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = userExerciseInstanceRepository.findAll().size();

        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);
        userExerciseInstanceDTO = userExerciseInstanceService.save(userExerciseInstanceDTO);

        int dbSizeAfterCreate = userExerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate + 1);
        assertThat(userExerciseInstanceDTO).isNotNull();
        assertThat(userExerciseInstanceDTO.getId()).isNotNull();
        assertThat(userExerciseInstanceDTO.getUserWorkoutInstanceId()).isEqualTo(userExerciseInstance.getUserWorkoutInstance().getId());
    }

    @Test
    public void saveWithoutLogin() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = userExerciseInstanceRepository.findAll().size();

        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);
        userExerciseInstanceDTO = userExerciseInstanceService.save(userExerciseInstanceDTO);

        int dbSizeAfterCreate = userExerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(userExerciseInstanceDTO).isNull();
    }

    @Test
    public void saveUnownedInstance() {
        AuthUtil.logInUser("user", "user", userRepository);
        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(entityManager);

        int dbSizeBeforeCreate = userExerciseInstanceRepository.findAll().size();

        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);
        userExerciseInstanceDTO = userExerciseInstanceService.save(userExerciseInstanceDTO);

        int dbSizeAfterCreate = userExerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(userExerciseInstanceDTO).isNull();
    }

    @Test
    public void updateAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstance);
        entityManager.flush();

        int dbSizeBeforeCreate = userExerciseInstanceRepository.findAll().size();

        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);
        userExerciseInstanceDTO = userExerciseInstanceService.save(userExerciseInstanceDTO);

        int dbSizeAfterCreate = userExerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(userExerciseInstanceDTO).isNotNull();
        assertThat(userExerciseInstanceDTO.getId()).isNotNull();
        assertThat(userExerciseInstanceDTO.getUserWorkoutInstanceId()).isEqualTo(userExerciseInstance.getUserWorkoutInstance().getId());
    }

    @Test
    public void findAll() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        List<UserExerciseInstance> workoutInstances = UserExerciseInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES, userDemographic);

        UserExerciseInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES);

        Page<UserExerciseInstanceDTO> workoutInstancePage = userExerciseInstanceService.findAll(null);
        List<UserExerciseInstanceDTO> workoutInstanceDTOs = workoutInstancePage.getContent();
        assertThat(workoutInstanceDTOs.size()).isEqualTo(NUMBER_OF_EXERCISE_INSTANCES);
        for (UserExerciseInstanceDTO userExerciseInstanceDTO :
            workoutInstanceDTOs) {
            assertThat(workoutInstances.contains(userExerciseInstanceMapper.userExerciseInstanceDTOToUserExerciseInstance(userExerciseInstanceDTO)));
        }
    }

    @Test
    public void findOne() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstance);
        entityManager.flush();

        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceService.findOne(userExerciseInstance.getId());
        assertThat(userExerciseInstanceDTO).isNotNull();
        assertThat(userExerciseInstanceDTO.getId()).isEqualTo(userExerciseInstance.getId());
    }

    @Test
    public void delete() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstance);
        entityManager.flush();

        int dbSizeBeforeDelete = userExerciseInstanceRepository.findAll().size();

        userExerciseInstanceService.delete(userExerciseInstance.getId());

        int dbSizeAfterDelete = userExerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete - 1);

        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceService.findOne(userExerciseInstance.getId());
        assertThat(userExerciseInstanceDTO).isNull();
    }


    @Test
    public void deleteWithoutUser() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstance);
        entityManager.flush();

        int dbSizeBeforeDelete = userExerciseInstanceRepository.findAll().size();

        userExerciseInstanceService.delete(userExerciseInstance.getId());

        int dbSizeAfterDelete = userExerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete);

        UserExerciseInstance dbWorkoutInstance = userExerciseInstanceRepository.findOne(userExerciseInstance.getId());
        assertThat(dbWorkoutInstance).isNotNull();
        assertThat(dbWorkoutInstance.getId()).isEqualTo(dbWorkoutInstance.getId());
    }
}
