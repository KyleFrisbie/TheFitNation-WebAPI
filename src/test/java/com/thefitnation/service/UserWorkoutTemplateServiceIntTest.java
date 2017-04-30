package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.UserWorkoutTemplateRepository;
import com.thefitnation.service.dto.UserWorkoutTemplateDTO;
import com.thefitnation.service.dto.UserWorkoutTemplateWithChildrenDTO;
import com.thefitnation.service.mapper.UserWorkoutTemplateMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserWorkoutTemplateGenerator;
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
public class UserWorkoutTemplateServiceIntTest {
    private static final String UPDATED_NAME = "BBBBBBBBBB";
    private static final Boolean UPDATED_IS_PRIVATE = true;
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final int NUMBER_OF_WORKOUT_TEMPLATES = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserWorkoutTemplateService userWorkoutTemplateService;

    @Autowired
    private UserWorkoutTemplateRepository userWorkoutTemplateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWorkoutTemplateMapper userWorkoutTemplateMapper;

    @Test
    public void saveUserWorkoutTemplateAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);

        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);
        UserWorkoutTemplateDTO testWorkoutTemplateDTO = userWorkoutTemplateService.save(userWorkoutTemplateDTO);

        int databaseSizeAfterCreate = userWorkoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
        assertThat(testWorkoutTemplateDTO.getId()).isNotNull();
        assertThat(testWorkoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
    }

    @Test
    public void saveUserWorkoutTemplateWithoutLoggedInUser() {
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);

        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);
        UserWorkoutTemplateDTO testWorkoutTemplateDTO = userWorkoutTemplateService.save(userWorkoutTemplateDTO);

        int databaseSizeAfterCreate = userWorkoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate);
        assertThat(testWorkoutTemplateDTO).isNull();
    }

    @Test
    public void saveUserWorkoutTemplateUserDoesntOwn() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager);
        entityManager.persist(userWorkoutTemplate);
        entityManager.flush();

        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);
        UserWorkoutTemplateDTO testUserWorkoutTemplateDTO = userWorkoutTemplateService.save(userWorkoutTemplateDTO);

        int databaseSizeAfterCreate = userWorkoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
        assertThat(testUserWorkoutTemplateDTO.getId()).isNotNull();
        assertThat(testUserWorkoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
    }

    @Test
    public void updateUserWorkoutTemplate() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutTemplate);
        entityManager.flush();

        userWorkoutTemplate.setNotes(UPDATED_NOTES);

        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);
        UserWorkoutTemplateDTO testWorkoutTemplateDTO = userWorkoutTemplateService.save(userWorkoutTemplateDTO);

        int databaseSizeAfterCreate = userWorkoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate);
        assertThat(testWorkoutTemplateDTO.getId()).isNotNull();
        assertThat(testWorkoutTemplateDTO.getId()).isEqualTo(userWorkoutTemplate.getId());
        assertThat(testWorkoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
        assertThat(testWorkoutTemplateDTO.getNotes()).isEqualToIgnoringCase(UPDATED_NOTES);
    }

    @Test
    public void updateUserWorkoutTemplateUserDoesntOwn() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager);
        entityManager.persist(userWorkoutTemplate);
        entityManager.flush();

        userWorkoutTemplate.setNotes(UPDATED_NOTES);

        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);
        UserWorkoutTemplateDTO testWorkoutTemplateDTO = userWorkoutTemplateService.save(userWorkoutTemplateDTO);

        int databaseSizeAfterCreate = userWorkoutTemplateRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
        assertThat(testWorkoutTemplateDTO.getId()).isNotNull();
        assertThat(testWorkoutTemplateDTO.getId()).isNotEqualTo(userWorkoutTemplate.getId());
        assertThat(testWorkoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
        assertThat(testWorkoutTemplateDTO.getNotes()).isEqualToIgnoringCase(UPDATED_NOTES);
    }

    @Test
    public void findAllUserOwnedUserWorkoutTemplates() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        List<UserWorkoutTemplate> workoutTemplates = UserWorkoutTemplateGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_TEMPLATES, userDemographic);

        UserWorkoutTemplateGenerator.getInstance().getMany(entityManager, NUMBER_OF_WORKOUT_TEMPLATES);
        Page<UserWorkoutTemplateDTO> testWorkoutTemplatePage = userWorkoutTemplateService.findAll(null);
        List<UserWorkoutTemplateDTO> testWorkoutTemplateDTOs = testWorkoutTemplatePage.getContent();
        assertThat(testWorkoutTemplateDTOs.size()).isEqualTo(NUMBER_OF_WORKOUT_TEMPLATES);
        for (UserWorkoutTemplateDTO userWorkoutTemplateDTO :
            testWorkoutTemplateDTOs) {
            assertThat(userWorkoutTemplateDTO.getUserDemographicId()).isEqualTo(userDemographic.getId());
        }
    }

    @Test
    public void findOneUserOwnedUserWorkoutTemplates() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutTemplate);
        entityManager.flush();

        UserWorkoutTemplateWithChildrenDTO testUserWorkoutTemplate = userWorkoutTemplateService.findOne(userWorkoutTemplate.getId());

        assertThat(testUserWorkoutTemplate).isNotNull();
        assertThat(testUserWorkoutTemplate.getId()).isEqualTo(userWorkoutTemplate.getId());
    }

    @Test
    public void deleteOwnedUserWorkoutTemplate() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(entityManager, user.get());
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(entityManager, userDemographic);
        entityManager.persist(userWorkoutTemplate);
        entityManager.flush();

        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        userWorkoutTemplateService.delete(userWorkoutTemplate.getId());

        int databaseSizeAfterCreate = userWorkoutTemplateRepository.findAll().size();

        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate - 1);
        assertThat(userWorkoutTemplateService.findOne(userWorkoutTemplate.getId())).isNull();
    }
}
