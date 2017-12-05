package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.domain.WorkoutTemplate;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.UserWorkoutTemplateRepository;
import com.thefitnation.repository.WorkoutTemplateRepository;
import com.thefitnation.service.dto.WorkoutTemplateDTO;
import com.thefitnation.service.dto.WorkoutTemplateWithChildrenDTO;
import com.thefitnation.service.mapper.WorkoutTemplateMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserDemographicGenerator;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class WorkoutTemplateServiceIntTest {
    private static final String UPDATED_NAME = "BBBBBBBBBB";
    private static final Boolean UPDATED_IS_PRIVATE = true;
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final int NUMBER_OF_WORKOUT_TEMPLATES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private WorkoutTemplateService workoutTemplateService;

    @Autowired
    private WorkoutTemplateRepository workoutTemplateRepository;

    @Autowired
    private UserWorkoutTemplateRepository userWorkoutTemplateRepository;

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
        WorkoutTemplateDTO testWorkoutTemplateDTO = workoutTemplateService.save(workoutTemplateDTO);

        int databaseSizeAfterCreate = workoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
        assertThat(testWorkoutTemplateDTO.getId()).isNotNull();
        assertThat(testWorkoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
    }

    @Test
    public void saveWorkoutTemplateWithoutLoggedInUser() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);

        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        WorkoutTemplateDTO testWorkoutTemplateDTO = workoutTemplateService.save(workoutTemplateDTO);

        int databaseSizeAfterCreate = workoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate);
        assertThat(testWorkoutTemplateDTO).isNull();
    }

    @Test
    public void saveWorkoutTemplateUserDoesntOwn() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager);
        entityManager.persist(workoutTemplate);
        entityManager.flush();

        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        WorkoutTemplateDTO testWorkoutTemplateDTO = workoutTemplateService.save(workoutTemplateDTO);

        int databaseSizeAfterCreate = workoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
        assertThat(testWorkoutTemplateDTO.getId()).isNotNull();
        assertThat(testWorkoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
    }

    @Test
    public void updateWorkoutTemplate() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(workoutTemplate);
        entityManager.flush();

        workoutTemplate.setName(UPDATED_NAME);
        workoutTemplate.setIsPrivate(UPDATED_IS_PRIVATE);
        workoutTemplate.setNotes(UPDATED_NOTES);

        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        WorkoutTemplateDTO testWorkoutTemplateDTO = workoutTemplateService.save(workoutTemplateDTO);

        int databaseSizeAfterCreate = workoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate);
        assertThat(testWorkoutTemplateDTO.getId()).isNotNull();
        assertThat(testWorkoutTemplateDTO.getId()).isEqualTo(workoutTemplate.getId());
        assertThat(testWorkoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
        assertThat(testWorkoutTemplateDTO.getName()).isEqualToIgnoringCase(UPDATED_NAME);
        assertThat(testWorkoutTemplateDTO.getNotes()).isEqualToIgnoringCase(UPDATED_NOTES);
        assertThat(testWorkoutTemplateDTO.getIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
    }

    @Test
    public void updateWorkoutTemplateUserDoesntOwn() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager);
        entityManager.persist(workoutTemplate);
        entityManager.flush();

        workoutTemplate.setName(UPDATED_NAME);
        workoutTemplate.setIsPrivate(UPDATED_IS_PRIVATE);
        workoutTemplate.setNotes(UPDATED_NOTES);

        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        WorkoutTemplateDTO testWorkoutTemplateDTO = workoutTemplateService.save(workoutTemplateDTO);

        int databaseSizeAfterCreate = workoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
        assertThat(testWorkoutTemplateDTO.getId()).isNotNull();
        assertThat(testWorkoutTemplateDTO.getId()).isNotEqualTo(workoutTemplate.getId());
        assertThat(testWorkoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
        assertThat(testWorkoutTemplateDTO.getName()).isEqualToIgnoringCase(UPDATED_NAME);
        assertThat(testWorkoutTemplateDTO.getNotes()).isEqualToIgnoringCase(UPDATED_NOTES);
        assertThat(testWorkoutTemplateDTO.getIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
    }

    @Test
    public void findAllUserOwnedWorkoutTemplates() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        List<WorkoutTemplate> workoutTemplates = WorkoutTemplateGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_TEMPLATES, userDemographic);

        WorkoutTemplateGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_TEMPLATES);
        Page<WorkoutTemplateDTO> testWorkoutTemplatePage = workoutTemplateService.findAll(null);
        List<WorkoutTemplateDTO> testWorkoutTemplateDTOs = testWorkoutTemplatePage.getContent();
        assertThat(testWorkoutTemplateDTOs.size()).isEqualTo(NUMBER_OF_WORKOUT_TEMPLATES);
        for (WorkoutTemplateDTO workoutTemplateDTO :
            testWorkoutTemplateDTOs) {
            assertThat(workoutTemplates.contains(workoutTemplateMapper.workoutTemplateDTOToWorkoutTemplate(workoutTemplateDTO)));
            assertThat(workoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
        }
    }

    @Test
    public void findOneUserOwnedWorkoutTemplates() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(workoutTemplate);
        entityManager.flush();

        WorkoutTemplateWithChildrenDTO testWorkoutTemplate = workoutTemplateService.findOne(workoutTemplate.getId());

        assertThat(testWorkoutTemplate).isNotNull();
        assertThat(testWorkoutTemplate.getId()).isEqualTo(workoutTemplate.getId());
    }

    @Test
    public void deleteOwnedWorkoutTemplate() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(workoutTemplate);
        entityManager.flush();

        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        workoutTemplateService.delete(workoutTemplate.getId());

        int databaseSizeAfterCreate = workoutTemplateRepository.findAll().size();

        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate - 1);
        assertThat(workoutTemplateService.findOne(workoutTemplate.getId())).isNull();
    }

    @Test
    public void deleteOwnedWorkoutTemplateWithChildren() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(workoutTemplate);
        entityManager.flush();

        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic, workoutTemplate);
        entityManager.persist(userWorkoutTemplate);
        entityManager.flush();

        workoutTemplate.addUserWorkoutTemplate(userWorkoutTemplate);

        int numberOfDBWorkoutTemplatesBeforeDelete = workoutTemplateRepository.findAll().size();
        int numberOfDBUserWorkoutTemplatesBeforeDelete = userWorkoutTemplateRepository.findAll().size();

        workoutTemplateService.delete(workoutTemplate.getId());

        int numberOfDBWorkoutTemplatesAfterDelete = workoutTemplateRepository.findAll().size();
        int numberOfDBUserWorkoutTemplatesAfterDelete = userWorkoutTemplateRepository.findAll().size();

        assertThat(numberOfDBWorkoutTemplatesAfterDelete).isEqualTo(numberOfDBWorkoutTemplatesBeforeDelete - 1);
        assertThat(numberOfDBUserWorkoutTemplatesAfterDelete).isEqualTo(numberOfDBUserWorkoutTemplatesBeforeDelete);
        assertThat(workoutTemplateService.findOne(workoutTemplate.getId())).isNull();
        UserWorkoutTemplate dbUserWorkoutTemplate = userWorkoutTemplateRepository.findOne(userWorkoutTemplate.getId());
        assertThat(dbUserWorkoutTemplate).isNotNull();
        assertThat(dbUserWorkoutTemplate.getWorkoutTemplate()).isNull();
    }
}
