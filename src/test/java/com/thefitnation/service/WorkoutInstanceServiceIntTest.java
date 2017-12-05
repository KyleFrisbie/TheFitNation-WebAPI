package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.WorkoutInstanceRepository;
import com.thefitnation.service.dto.WorkoutInstanceDTO;
import com.thefitnation.service.dto.WorkoutTemplateDTO;
import com.thefitnation.service.mapper.WorkoutInstanceMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.WorkoutInstanceGenerator;
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
public class WorkoutInstanceServiceIntTest {
    private static final int NUMBER_OF_WORKOUT_INSTANCES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutInstanceService workoutInstanceService;

    @Autowired
    private WorkoutInstanceRepository workoutInstanceRepository;

    @Autowired
    private WorkoutInstanceMapper workoutInstanceMapper;

    @Test
    public void saveAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = workoutInstanceRepository.findAll().size();

        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
        workoutInstanceDTO = workoutInstanceService.save(workoutInstanceDTO);

        int dbSizeAfterCreate = workoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate + 1);
        assertThat(workoutInstanceDTO).isNotNull();
        assertThat(workoutInstanceDTO.getId()).isNotNull();
        assertThat(workoutInstanceDTO.getWorkoutTemplateId()).isEqualTo(workoutInstance.getWorkoutTemplate().getId());
        assertThat(workoutInstanceDTO.getCreatedOn()).isEqualTo(LocalDate.now());
        assertThat(workoutInstanceDTO.getLastUpdated()).isEqualTo(LocalDate.now());
    }

    @Test
    public void saveWithoutLogin() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = workoutInstanceRepository.findAll().size();

        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
        workoutInstanceDTO = workoutInstanceService.save(workoutInstanceDTO);

        int dbSizeAfterCreate = workoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(workoutInstanceDTO).isNull();
    }

    @Test
    public void saveUnownedInstance() {
        AuthUtil.logInUser("user", "user", userRepository);
        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager);

        int dbSizeBeforeCreate = workoutInstanceRepository.findAll().size();

        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
        workoutInstanceDTO = workoutInstanceService.save(workoutInstanceDTO);

        int dbSizeAfterCreate = workoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(workoutInstanceDTO).isNull();
    }

    @Test
    public void updateAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(workoutInstance);
        entityManager.flush();

        int dbSizeBeforeCreate = workoutInstanceRepository.findAll().size();

        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
        workoutInstanceDTO = workoutInstanceService.save(workoutInstanceDTO);

        int dbSizeAfterCreate = workoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(workoutInstanceDTO).isNotNull();
        assertThat(workoutInstanceDTO.getId()).isNotNull();
        assertThat(workoutInstanceDTO.getWorkoutTemplateId()).isEqualTo(workoutInstance.getWorkoutTemplate().getId());
        assertThat(workoutInstanceDTO.getCreatedOn()).isNotEqualTo(LocalDate.now());
        assertThat(workoutInstanceDTO.getLastUpdated()).isEqualTo(LocalDate.now());
    }

    @Test
    public void findAll() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        List<WorkoutInstance> workoutInstances = WorkoutInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_INSTANCES, userDemographic);

        WorkoutInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_INSTANCES);

        Page<WorkoutInstanceDTO> workoutInstancePage = workoutInstanceService.findAll(null);
        List<WorkoutInstanceDTO> workoutInstanceDTOs = workoutInstancePage.getContent();
        assertThat(workoutInstanceDTOs.size()).isEqualTo(NUMBER_OF_WORKOUT_INSTANCES);
        for (WorkoutInstanceDTO workoutInstanceDTO :
            workoutInstanceDTOs) {
            assertThat(workoutInstances.contains(workoutInstanceMapper.workoutInstanceDTOToWorkoutInstance(workoutInstanceDTO)));
        }
    }

    @Test
    public void findOne() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(workoutInstance);
        entityManager.flush();

        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceService.findOne(workoutInstance.getId());
        assertThat(workoutInstanceDTO).isNotNull();
        assertThat(workoutInstanceDTO.getId()).isEqualTo(workoutInstance.getId());
    }

    @Test
    public void delete() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(workoutInstance);
        entityManager.flush();

        int dbSizeBeforeDelete = workoutInstanceRepository.findAll().size();

        workoutInstanceService.delete(workoutInstance.getId());

        int dbSizeAfterDelete = workoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete - 1);

        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceService.findOne(workoutInstance.getId());
        assertThat(workoutInstanceDTO).isNull();
    }


    @Test
    public void deleteWithoutUser() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(workoutInstance);
        entityManager.flush();

        int dbSizeBeforeDelete = workoutInstanceRepository.findAll().size();

        workoutInstanceService.delete(workoutInstance.getId());

        int dbSizeAfterDelete = workoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete);

        WorkoutInstance dbWorkoutInstance = workoutInstanceRepository.findOne(workoutInstance.getId());
        assertThat(dbWorkoutInstance).isNotNull();
        assertThat(dbWorkoutInstance.getId()).isEqualTo(dbWorkoutInstance.getId());
    }
}
