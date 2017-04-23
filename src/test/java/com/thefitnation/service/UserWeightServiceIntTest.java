package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.UserWeightRepository;
import com.thefitnation.service.dto.UserWeightDTO;
import com.thefitnation.service.mapper.UserWeightMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserWeightGenerator;
import com.thefitnation.web.rest.UserWeightResourceIntTest;
import org.junit.Before;
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

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class UserWeightServiceIntTest {

    private static final LocalDate DEFAULT_WEIGHT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WEIGHT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserWeightService userWeightService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDemographicRepository userDemographicRepository;

    @Autowired
    private UserWeightRepository userWeightRepository;

    @Autowired
    private UserWeightMapper userWeightMapper;

    private UserWeight userWeight;

    @Before
    public void initTest() {
        userWeight = UserWeightResourceIntTest.createEntity(em);
    }

    @Test
    public void saveNewUserWeightAsLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();

        UserWeight userWeight = new UserWeight()
            .weight(DEFAULT_WEIGHT)
            .weightDate(DEFAULT_WEIGHT_DATE)
            .userDemographic(userDemographic);

        int databaseSizeBeforeCreate = userWeightRepository.findAll().size();

        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        UserWeightDTO testUserWeight = userWeightService.save(userWeightDTO);

        List<UserWeight> userWeights = userWeightRepository.findAll();
        assertThat(userWeights).hasSize(databaseSizeBeforeCreate + 1);
        assertThat(testUserWeight.getId()).isNotNull();
        assertThat(testUserWeight.getUserDemographicId()).isEqualTo(userDemographic.getId());
    }

    @Test
    public void saveNewUserWeightNotOwnedByLoggedInUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();

        UserWeight userWeight = UserWeightGenerator.getInstance().getOne(em);
        int databaseSizeBeforeCreate = userWeightRepository.findAll().size();

        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        UserWeightDTO testUserWeight = userWeightService.save(userWeightDTO);

        List<UserWeight> userWeights = userWeightRepository.findAll();
        assertThat(userWeights).hasSize(databaseSizeBeforeCreate + 1);
        assertThat(testUserWeight.getId()).isNotNull();
        assertThat(testUserWeight.getId()).isNotEqualTo(userWeight.getId());
    }

    @Test
    public void saveNewUserWeightWithoutValidLogin() {
        int databaseSizeBeforeCreate = userWeightRepository.findAll().size();

        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        UserWeightDTO testUserWeight = userWeightService.save(userWeightDTO);

        List<UserWeight> userWeights = userWeightRepository.findAll();
        assertThat(userWeights).hasSize(databaseSizeBeforeCreate);
        assertThat(testUserWeight).isNull();
    }

    @Test
    public void saveNewUserWeightAsAdmin() {
        Optional<User> user = AuthUtil.logInUser("admin", "admin", userRepository);
        int databaseSizeBeforeCreate = userWeightRepository.findAll().size();

        UserWeight userWeight = UserWeightGenerator.getInstance().getOne(em);
        User weightOwner = userWeight.getUserDemographic().getUser();

        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        UserWeightDTO testUserWeight = userWeightService.save(userWeightDTO);

        List<UserWeight> userWeights = userWeightRepository.findAll();
        assertThat(userWeights).hasSize(databaseSizeBeforeCreate + 1);
        assertThat(testUserWeight).isNotNull();

        UserDemographic testUserDemographic = userDemographicRepository.findOne(testUserWeight.getUserDemographicId());
        assertThat(weightOwner.getId()).isEqualTo(testUserDemographic.getUser().getId());
    }

    @Test
    public void updateUserWeightForUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        UserWeight userWeight = UserWeightGenerator.getInstance().getOne(em, userDemographic);
        em.persist(userWeight);
        em.flush();

        int databaseSizeBeforeSave = userWeightRepository.findAll().size();

        userWeight.setWeight(UPDATED_WEIGHT);
        userWeight.setWeightDate(UPDATED_WEIGHT_DATE);

        UserWeightDTO existingUserWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        UserWeightDTO testExistingUserWeightDTO = userWeightService.save(existingUserWeightDTO);

        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeSave);
        assertThat(testExistingUserWeightDTO).isNotNull();
        assertThat(testExistingUserWeightDTO.getId()).isEqualTo(userWeight.getId());
        assertThat(testExistingUserWeightDTO.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testExistingUserWeightDTO.getWeightDate()).isEqualTo(UPDATED_WEIGHT_DATE);
    }

    @Test
    public void findAllUserWeightsForUser() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        int numberOfUserWeights = 10;
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        List<UserWeight> userWeights = UserWeightGenerator.getInstance().getMany(em, numberOfUserWeights, userDemographic);
        UserWeightGenerator.getInstance().getMany(em, numberOfUserWeights);

        Page<UserWeightDTO> userWeightDTOsPage = userWeightService.findAll(null);

        assertThat(userWeightDTOsPage.getTotalElements()).isEqualTo(numberOfUserWeights);

        List<UserWeight> testUserWeights = userWeightMapper.userWeightDTOsToUserWeights(userWeightDTOsPage.getContent());
        for (UserWeight testUserWeight : testUserWeights)
        {
            assertThat(userWeights).contains(testUserWeight);
        }
    }

    @Test
    public void findAllUserWeightsForAdmin() {
        Optional<User> user = AuthUtil.logInUser("admin", "admin", userRepository);
        int numberOfUserWeights = 10;
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        UserWeightGenerator.getInstance().getMany(em, numberOfUserWeights, userDemographic);
        UserWeightGenerator.getInstance().getMany(em, numberOfUserWeights);

        int databaseSize = userWeightRepository.findAll().size();

        Page<UserWeightDTO> userWeightDTOsPage = userWeightService.findAll(null);
        assertThat(userWeightDTOsPage.getTotalElements()).isEqualTo(databaseSize);
    }

    @Test
    public void findAllUserWeightsForInvalidUser() {
        int numberOfUserWeights = 10;
        UserWeightGenerator.getInstance().getMany(em, numberOfUserWeights);
        int databaseSize = userWeightRepository.findAll().size();

        Page<UserWeightDTO> userWeightDTOsPage = userWeightService.findAll(null);
        assertThat(userWeightDTOsPage).isNull();
    }

    @Test
    public void findOneUserWeightsById() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        UserWeight userWeight = UserWeightGenerator.getInstance().getOne(em, userDemographic);
        em.persist(userWeight);
        em.flush();

        UserWeight testUserWeight = userWeightMapper.userWeightDTOToUserWeight(userWeightService.findOne(userWeight.getId()));
        assertThat(testUserWeight).isNotNull();
        assertThat(testUserWeight.getId()).isEqualTo(userWeight.getId());
    }

    @Test
    public void findOneUserWeightsByIdNotOwnedByUser() {
        AuthUtil.logInUser("user", "user", userRepository);
        UserWeight userWeight = UserWeightGenerator.getInstance().getOne(em);
        em.persist(userWeight);
        em.flush();

        UserWeight testUserWeight = userWeightMapper.userWeightDTOToUserWeight(userWeightService.findOne(userWeight.getId()));
        assertThat(testUserWeight).isNull();
    }

    @Test
    public void findOneUserWeightsByIdNotOwnedByAdmin() {
        Optional<User> user = AuthUtil.logInUser("admin", "admin", userRepository);
        UserDemographicGenerator.getOne(em, user.get());
        UserWeight userWeight = UserWeightGenerator.getInstance().getOne(em);
        em.persist(userWeight);
        em.flush();

        UserWeight testUserWeight = userWeightMapper.userWeightDTOToUserWeight(userWeightService.findOne(userWeight.getId()));
        assertThat(testUserWeight).isNotNull();
        assertThat(testUserWeight.getId()).isEqualTo(userWeight.getId());
    }

    @Test
    public void deleteOneUserWeightsById() {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        UserWeight userWeight = UserWeightGenerator.getInstance().getOne(em, userDemographic);
        em.persist(userWeight);
        em.flush();
        int databaseSizeBeforeDelete = userWeightRepository.findAll().size();

        userWeightService.delete(userWeight.getId());
        assertThat(userWeightRepository.findAll().size()).isEqualTo(databaseSizeBeforeDelete - 1);
        assertThat(userWeightRepository.findOne(userWeight.getId())).isNull();
    }

    @Test
    public void deleteOneUserWeightsByIdNotOwnedByUser() {
        AuthUtil.logInUser("user", "user", userRepository);
        UserWeight userWeight = UserWeightGenerator.getInstance().getOne(em);
        em.persist(userWeight);
        em.flush();
        int databaseSizeBeforeDelete = userWeightRepository.findAll().size();

        userWeightService.delete(userWeight.getId());
        assertThat(userWeightRepository.findAll().size()).isEqualTo(databaseSizeBeforeDelete);
        assertThat(userWeightRepository.findOne(userWeight.getId())).isNotNull();
    }

    @Test
    public void deleteOneUserWeightsByIdNotOwnedByAdmin() {
        Optional<User> user = AuthUtil.logInUser("admin", "admin", userRepository);
        UserDemographicGenerator.getOne(em, user.get());
        UserWeight userWeight = UserWeightGenerator.getInstance().getOne(em);
        em.persist(userWeight);
        em.flush();
        int databaseSizeBeforeDelete = userWeightRepository.findAll().size();

        userWeightService.delete(userWeight.getId());
        assertThat(userWeightRepository.findAll().size()).isEqualTo(databaseSizeBeforeDelete - 1);
        assertThat(userWeightRepository.findOne(userWeight.getId())).isNull();
    }
}
