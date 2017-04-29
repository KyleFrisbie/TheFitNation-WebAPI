package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.repository.ExerciseInstanceRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.service.dto.ExerciseInstanceDTO;
import com.thefitnation.service.mapper.ExerciseInstanceMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.ExerciseInstanceGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class ExerciseInstanceServiceIntTest {
    private static final int NUMBER_OF_EXERCISE_INSTANCES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseInstanceService exerciseInstanceService;

    @Autowired
    private ExerciseInstanceRepository exerciseInstanceRepository;

    @Autowired
    private ExerciseInstanceMapper exerciseInstanceMapper;

    @Test
    public void saveAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = exerciseInstanceRepository.findAll().size();

        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
        exerciseInstanceDTO = exerciseInstanceService.save(exerciseInstanceDTO);

        int dbSizeAfterCreate = exerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate + 1);
        assertThat(exerciseInstanceDTO).isNotNull();
        assertThat(exerciseInstanceDTO.getId()).isNotNull();
        assertThat(exerciseInstanceDTO.getWorkoutInstanceId()).isEqualTo(exerciseInstance.getWorkoutInstance().getId());
    }

    @Test
    public void saveWithoutLogin() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = exerciseInstanceRepository.findAll().size();

        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
        exerciseInstanceDTO = exerciseInstanceService.save(exerciseInstanceDTO);

        int dbSizeAfterCreate = exerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(exerciseInstanceDTO).isNull();
    }

    @Test
    public void saveUnownedInstance() {
        AuthUtil.logInUser("user", "user", userRepository);
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager);

        int dbSizeBeforeCreate = exerciseInstanceRepository.findAll().size();

        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
        exerciseInstanceDTO = exerciseInstanceService.save(exerciseInstanceDTO);

        int dbSizeAfterCreate = exerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(exerciseInstanceDTO).isNull();
    }

    @Test
    public void updateAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstance);
        entityManager.flush();

        int dbSizeBeforeCreate = exerciseInstanceRepository.findAll().size();

        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
        exerciseInstanceDTO = exerciseInstanceService.save(exerciseInstanceDTO);

        int dbSizeAfterCreate = exerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(exerciseInstanceDTO).isNotNull();
        assertThat(exerciseInstanceDTO.getId()).isNotNull();
        assertThat(exerciseInstanceDTO.getWorkoutInstanceId()).isEqualTo(exerciseInstance.getWorkoutInstance().getId());
    }

    @Test
    public void findAll() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        List<ExerciseInstance> workoutInstances = ExerciseInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES, userDemographic);

        ExerciseInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES);

        Page<ExerciseInstanceDTO> workoutInstancePage = exerciseInstanceService.findAll(null);
        List<ExerciseInstanceDTO> workoutInstanceDTOs = workoutInstancePage.getContent();
        assertThat(workoutInstanceDTOs.size()).isEqualTo(NUMBER_OF_EXERCISE_INSTANCES);
        for (ExerciseInstanceDTO exerciseInstanceDTO :
            workoutInstanceDTOs) {
            assertThat(workoutInstances.contains(exerciseInstanceMapper.exerciseInstanceDTOToExerciseInstance(exerciseInstanceDTO)));
        }
    }

    @Test
    public void findOne() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstance);
        entityManager.flush();

        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceService.findOne(exerciseInstance.getId());
        assertThat(exerciseInstanceDTO).isNotNull();
        assertThat(exerciseInstanceDTO.getId()).isEqualTo(exerciseInstance.getId());
    }

    @Test
    public void delete() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstance);
        entityManager.flush();

        int dbSizeBeforeDelete = exerciseInstanceRepository.findAll().size();

        exerciseInstanceService.delete(exerciseInstance.getId());

        int dbSizeAfterDelete = exerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete - 1);

        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceService.findOne(exerciseInstance.getId());
        assertThat(exerciseInstanceDTO).isNull();
    }


    @Test
    public void deleteWithoutUser() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstance);
        entityManager.flush();

        int dbSizeBeforeDelete = exerciseInstanceRepository.findAll().size();

        exerciseInstanceService.delete(exerciseInstance.getId());

        int dbSizeAfterDelete = exerciseInstanceRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete);

        ExerciseInstance dbWorkoutInstance = exerciseInstanceRepository.findOne(exerciseInstance.getId());
        assertThat(dbWorkoutInstance).isNotNull();
        assertThat(dbWorkoutInstance.getId()).isEqualTo(dbWorkoutInstance.getId());
    }
}
