package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWorkoutInstance;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.UserWorkoutInstanceRepository;
import com.thefitnation.service.dto.UserWorkoutInstanceDTO;
import com.thefitnation.service.mapper.UserWorkoutInstanceMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserWorkoutInstanceGenerator;
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
public class UserWorkoutInstanceServiceIntTest {
    private static final int NUMBER_OF_WORKOUT_INSTANCES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWorkoutInstanceService userWorkoutInstanceService;

    @Autowired
    private UserWorkoutInstanceRepository userWorkoutInstanceRepository;

    @Autowired
    private UserWorkoutInstanceMapper userWorkoutInstanceMapper;

    @Test
    public void saveAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = userWorkoutInstanceRepository.findAll().size();

        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);
        userWorkoutInstanceDTO = userWorkoutInstanceService.save(userWorkoutInstanceDTO);

        int dbSizeAfterCreate = userWorkoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate + 1);
        assertThat(userWorkoutInstanceDTO).isNotNull();
        assertThat(userWorkoutInstanceDTO.getId()).isNotNull();
        assertThat(userWorkoutInstanceDTO.getUserWorkoutTemplateId()).isEqualTo(userWorkoutInstance.getUserWorkoutTemplate().getId());
        assertThat(userWorkoutInstanceDTO.getCreatedOn()).isEqualTo(LocalDate.now());
        assertThat(userWorkoutInstanceDTO.getLastUpdated()).isEqualTo(LocalDate.now());
    }

    @Test
    public void saveWithoutLogin() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);

        int dbSizeBeforeCreate = userWorkoutInstanceRepository.findAll().size();

        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);
        userWorkoutInstanceDTO = userWorkoutInstanceService.save(userWorkoutInstanceDTO);

        int dbSizeAfterCreate = userWorkoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(userWorkoutInstanceDTO).isNull();
    }

    @Test
    public void saveUnownedInstance() {
        AuthUtil.logInUser("user", "user", userRepository);
        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager);

        int dbSizeBeforeCreate = userWorkoutInstanceRepository.findAll().size();

        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);
        userWorkoutInstanceDTO = userWorkoutInstanceService.save(userWorkoutInstanceDTO);

        int dbSizeAfterCreate = userWorkoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(userWorkoutInstanceDTO).isNull();
    }

    @Test
    public void updateAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutInstance);
        entityManager.flush();

        int dbSizeBeforeCreate = userWorkoutInstanceRepository.findAll().size();

        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);
        userWorkoutInstanceDTO = userWorkoutInstanceService.save(userWorkoutInstanceDTO);

        int dbSizeAfterCreate = userWorkoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterCreate).isEqualTo(dbSizeBeforeCreate);
        assertThat(userWorkoutInstanceDTO).isNotNull();
        assertThat(userWorkoutInstanceDTO.getId()).isNotNull();
        assertThat(userWorkoutInstanceDTO.getUserWorkoutTemplateId()).isEqualTo(userWorkoutInstance.getUserWorkoutTemplate().getId());
        assertThat(userWorkoutInstanceDTO.getCreatedOn()).isNotEqualTo(LocalDate.now());
        assertThat(userWorkoutInstanceDTO.getLastUpdated()).isEqualTo(LocalDate.now());
    }

    @Test
    public void findAll() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        List<UserWorkoutInstance> workoutInstances = UserWorkoutInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_INSTANCES, userDemographic);

        UserWorkoutInstanceGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_INSTANCES);

        Page<UserWorkoutInstanceDTO> workoutInstancePage = userWorkoutInstanceService.findAll(null);
        List<UserWorkoutInstanceDTO> workoutInstanceDTOs = workoutInstancePage.getContent();
        assertThat(workoutInstanceDTOs.size()).isEqualTo(NUMBER_OF_WORKOUT_INSTANCES);
        for (UserWorkoutInstanceDTO userWorkoutInstanceDTO :
            workoutInstanceDTOs) {
            assertThat(workoutInstances.contains(userWorkoutInstanceMapper.userWorkoutInstanceDTOToUserWorkoutInstance(userWorkoutInstanceDTO)));
        }
    }

    @Test
    public void findOne() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutInstance);
        entityManager.flush();

        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceService.findOne(userWorkoutInstance.getId());
        assertThat(userWorkoutInstanceDTO).isNotNull();
        assertThat(userWorkoutInstanceDTO.getId()).isEqualTo(userWorkoutInstance.getId());
    }

    @Test
    public void delete() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutInstance);
        entityManager.flush();

        int dbSizeBeforeDelete = userWorkoutInstanceRepository.findAll().size();

        userWorkoutInstanceService.delete(userWorkoutInstance.getId());

        int dbSizeAfterDelete = userWorkoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete - 1);

        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceService.findOne(userWorkoutInstance.getId());
        assertThat(userWorkoutInstanceDTO).isNull();
    }


    @Test
    public void deleteWithoutUser() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutInstance);
        entityManager.flush();

        int dbSizeBeforeDelete = userWorkoutInstanceRepository.findAll().size();

        userWorkoutInstanceService.delete(userWorkoutInstance.getId());

        int dbSizeAfterDelete = userWorkoutInstanceRepository.findAll().size();

        assertThat(dbSizeAfterDelete).isEqualTo(dbSizeBeforeDelete);

        UserWorkoutInstance dbWorkoutInstance = userWorkoutInstanceRepository.findOne(userWorkoutInstance.getId());
        assertThat(dbWorkoutInstance).isNotNull();
        assertThat(dbWorkoutInstance.getId()).isEqualTo(dbWorkoutInstance.getId());
    }
}
