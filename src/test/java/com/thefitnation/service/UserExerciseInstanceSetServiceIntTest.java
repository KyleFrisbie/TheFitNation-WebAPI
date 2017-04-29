package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.UserExerciseInstanceSet;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.UserExerciseInstanceSetRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.service.dto.UserExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.UserExerciseInstanceSetMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserExerciseInstanceSetGenerator;
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
public class UserExerciseInstanceSetServiceIntTest {
    private static final int NUMBER_OF_EXERCISE_INSTANCES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserExerciseInstanceSetService userExerciseInstanceSetService;

    @Autowired
    private UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;

    @Autowired
    private UserExerciseInstanceSetMapper userExerciseInstanceSetMapper;

    @Test
    public void saveAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstanceSet userExerciseInstanceSet = UserExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = userExerciseInstanceSetRepository.findAll().size();

        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);
        userExerciseInstanceSetDTO = userExerciseInstanceSetService.save(userExerciseInstanceSetDTO);

        int dbSizeAfterCreate = userExerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate + 1);
        assertThat(userExerciseInstanceSetDTO).isNotNull();
        assertThat(userExerciseInstanceSetDTO.getId()).isNotNull();
        assertThat(userExerciseInstanceSetDTO.getUserExerciseInstanceId()).isEqualTo(userExerciseInstanceSet.getUserExerciseInstance().getId());
    }

    @Test
    public void saveWithoutLogin() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstanceSet userExerciseInstanceSet = UserExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = userExerciseInstanceSetRepository.findAll().size();

        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);
        userExerciseInstanceSetDTO = userExerciseInstanceSetService.save(userExerciseInstanceSetDTO);

        int dbSizeAfterCreate = userExerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(userExerciseInstanceSetDTO).isNull();
    }

    @Test
    public void saveUnownedInstance() {
        AuthUtil.logInUser("user", "user", userRepository);
        UserExerciseInstanceSet userExerciseInstanceSet = UserExerciseInstanceSetGenerator.getInstance().getOne(entityManager);

        int dbSizeBeforeCreate = userExerciseInstanceSetRepository.findAll().size();

        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);
        userExerciseInstanceSetDTO = userExerciseInstanceSetService.save(userExerciseInstanceSetDTO);

        int dbSizeAfterCreate = userExerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(userExerciseInstanceSetDTO).isNull();
    }

    @Test
    public void updateAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstanceSet userExerciseInstanceSet = UserExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstanceSet);
        entityManager.flush();

        int dbSizeBeforeCreate = userExerciseInstanceSetRepository.findAll().size();

        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);
        userExerciseInstanceSetDTO = userExerciseInstanceSetService.save(userExerciseInstanceSetDTO);

        int dbSizeAfterCreate = userExerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(userExerciseInstanceSetDTO).isNotNull();
        assertThat(userExerciseInstanceSetDTO.getId()).isNotNull();
        assertThat(userExerciseInstanceSetDTO.getUserExerciseInstanceId()).isEqualTo(userExerciseInstanceSet.getUserExerciseInstance().getId());
    }

    @Test
    public void findAll() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        List<UserExerciseInstanceSet> workoutInstances = UserExerciseInstanceSetGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES, userDemographic);

        UserExerciseInstanceSetGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES);

        Page<UserExerciseInstanceSetDTO> workoutInstancePage = userExerciseInstanceSetService.findAll(null);
        List<UserExerciseInstanceSetDTO> workoutInstanceDTOs = workoutInstancePage.getContent();
        assertThat(workoutInstanceDTOs.size()).isEqualTo(NUMBER_OF_EXERCISE_INSTANCES);
        for (UserExerciseInstanceSetDTO userExerciseInstanceSetDTO :
            workoutInstanceDTOs) {
            assertThat(workoutInstances.contains(userExerciseInstanceSetMapper.userExerciseInstanceSetDTOToUserExerciseInstanceSet(userExerciseInstanceSetDTO)));
        }
    }

    @Test
    public void findOne() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstanceSet userExerciseInstanceSet = UserExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstanceSet);
        entityManager.flush();

        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetService.findOne(userExerciseInstanceSet.getId());
        assertThat(userExerciseInstanceSetDTO).isNotNull();
        assertThat(userExerciseInstanceSetDTO.getId()).isEqualTo(userExerciseInstanceSet.getId());
    }

    @Test
    public void delete() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstanceSet userExerciseInstanceSet = UserExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstanceSet);
        entityManager.flush();

        int dbSizeBeforeDelete = userExerciseInstanceSetRepository.findAll().size();

        userExerciseInstanceSetService.delete(userExerciseInstanceSet.getId());

        int dbSizeAfterDelete = userExerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete - 1);

        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetService.findOne(userExerciseInstanceSet.getId());
        assertThat(userExerciseInstanceSetDTO).isNull();
    }


    @Test
    public void deleteWithoutUser() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserExerciseInstanceSet userExerciseInstanceSet = UserExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userExerciseInstanceSet);
        entityManager.flush();

        int dbSizeBeforeDelete = userExerciseInstanceSetRepository.findAll().size();

        userExerciseInstanceSetService.delete(userExerciseInstanceSet.getId());

        int dbSizeAfterDelete = userExerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete);

        UserExerciseInstanceSet dbWorkoutInstance = userExerciseInstanceSetRepository.findOne(userExerciseInstanceSet.getId());
        assertThat(dbWorkoutInstance).isNotNull();
        assertThat(dbWorkoutInstance.getId()).isEqualTo(dbWorkoutInstance.getId());
    }
}
