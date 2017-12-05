package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.ExerciseInstanceSetRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.service.dto.ExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.ExerciseInstanceMapper;
import com.thefitnation.service.mapper.ExerciseInstanceSetMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.ExerciseInstanceSetGenerator;
import com.thefitnation.testTools.ExerciseInstanceSetGenerator;
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
public class ExerciseInstanceSetServiceIntTest {
    private static final int NUMBER_OF_EXERCISE_INSTANCES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseInstanceSetService exerciseInstanceSetService;

    @Autowired
    private ExerciseInstanceSetRepository exerciseInstanceSetRepository;

    @Autowired
    private ExerciseInstanceSetMapper exerciseInstanceSetMapper;

    @Test
    public void saveAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstanceSet exerciseInstanceSet = ExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = exerciseInstanceSetRepository.findAll().size();

        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);
        exerciseInstanceSetDTO = exerciseInstanceSetService.save(exerciseInstanceSetDTO);

        int dbSizeAfterCreate = exerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate + 1);
        assertThat(exerciseInstanceSetDTO).isNotNull();
        assertThat(exerciseInstanceSetDTO.getId()).isNotNull();
        assertThat(exerciseInstanceSetDTO.getExerciseInstanceId()).isEqualTo(exerciseInstanceSet.getExerciseInstance().getId());
    }

    @Test
    public void saveWithoutLogin() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstanceSet exerciseInstanceSet = ExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = exerciseInstanceSetRepository.findAll().size();

        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);
        exerciseInstanceSetDTO = exerciseInstanceSetService.save(exerciseInstanceSetDTO);

        int dbSizeAfterCreate = exerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(exerciseInstanceSetDTO).isNull();
    }

    @Test
    public void saveUnownedInstance() {
        AuthUtil.logInUser("user", "user", userRepository);
        ExerciseInstanceSet exerciseInstanceSet = ExerciseInstanceSetGenerator.getInstance().getOne(entityManager);

        int dbSizeBeforeCreate = exerciseInstanceSetRepository.findAll().size();

        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);
        exerciseInstanceSetDTO = exerciseInstanceSetService.save(exerciseInstanceSetDTO);

        int dbSizeAfterCreate = exerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(exerciseInstanceSetDTO).isNull();
    }

    @Test
    public void updateAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstanceSet exerciseInstanceSet = ExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstanceSet);
        entityManager.flush();

        int dbSizeBeforeCreate = exerciseInstanceSetRepository.findAll().size();

        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);
        exerciseInstanceSetDTO = exerciseInstanceSetService.save(exerciseInstanceSetDTO);

        int dbSizeAfterCreate = exerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(exerciseInstanceSetDTO).isNotNull();
        assertThat(exerciseInstanceSetDTO.getId()).isNotNull();
        assertThat(exerciseInstanceSetDTO.getExerciseInstanceId()).isEqualTo(exerciseInstanceSet.getExerciseInstance().getId());
    }

    @Test
    public void findAll() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        List<ExerciseInstanceSet> workoutInstances = ExerciseInstanceSetGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES, userDemographic);

        ExerciseInstanceSetGenerator.getInstance().getMany(entityManager, NUMBER_OF_EXERCISE_INSTANCES);

        Page<ExerciseInstanceSetDTO> workoutInstancePage = exerciseInstanceSetService.findAll(null);
        List<ExerciseInstanceSetDTO> workoutInstanceDTOs = workoutInstancePage.getContent();
        assertThat(workoutInstanceDTOs.size()).isEqualTo(NUMBER_OF_EXERCISE_INSTANCES);
        for (ExerciseInstanceSetDTO exerciseInstanceSetDTO :
            workoutInstanceDTOs) {
            assertThat(workoutInstances.contains(exerciseInstanceSetMapper.exerciseInstanceSetDTOToExerciseInstanceSet(exerciseInstanceSetDTO)));
        }
    }

    @Test
    public void findOne() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstanceSet exerciseInstanceSet = ExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstanceSet);
        entityManager.flush();

        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetService.findOne(exerciseInstanceSet.getId());
        assertThat(exerciseInstanceSetDTO).isNotNull();
        assertThat(exerciseInstanceSetDTO.getId()).isEqualTo(exerciseInstanceSet.getId());
    }

    @Test
    public void delete() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstanceSet exerciseInstanceSet = ExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstanceSet);
        entityManager.flush();

        int dbSizeBeforeDelete = exerciseInstanceSetRepository.findAll().size();

        exerciseInstanceSetService.delete(exerciseInstanceSet.getId());

        int dbSizeAfterDelete = exerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete - 1);

        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetService.findOne(exerciseInstanceSet.getId());
        assertThat(exerciseInstanceSetDTO).isNull();
    }


    @Test
    public void deleteWithoutUser() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        ExerciseInstanceSet exerciseInstanceSet = ExerciseInstanceSetGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(exerciseInstanceSet);
        entityManager.flush();

        int dbSizeBeforeDelete = exerciseInstanceSetRepository.findAll().size();

        exerciseInstanceSetService.delete(exerciseInstanceSet.getId());

        int dbSizeAfterDelete = exerciseInstanceSetRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete);

        ExerciseInstanceSet dbWorkoutInstance = exerciseInstanceSetRepository.findOne(exerciseInstanceSet.getId());
        assertThat(dbWorkoutInstance).isNotNull();
        assertThat(dbWorkoutInstance.getId()).isEqualTo(dbWorkoutInstance.getId());
    }
}
