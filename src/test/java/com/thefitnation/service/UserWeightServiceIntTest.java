package com.thefitnation.service;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.repository.UserWeightRepository;
import com.thefitnation.service.dto.UserWeightDTO;
import com.thefitnation.service.mapper.UserWeightMapper;
import com.thefitnation.testTools.CreateEntities;
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

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class UserWeightServiceIntTest {

    private static final LocalDate UPDATED_WEIGHT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final Float UPDATED_WEIGHT = 2F;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserWeightService userWeightService;

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
    public void saveNewUserWeight() {
        int databaseSizeBeforeCreate = userWeightRepository.findAll().size();

        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        UserWeightDTO testUserWeight = userWeightService.save(userWeightDTO);

        List<UserWeight> userWeights = userWeightRepository.findAll();
        assertThat(userWeights).hasSize(databaseSizeBeforeCreate + 1);
        assertThat(testUserWeight.getId()).isNotNull();
    }

    @Test
    public void saveUserWeightWithExistingId() {
        em.persist(userWeight);
        em.flush();

        int databaseSizeBeforeSave = userWeightRepository.findAll().size();

        UserWeightDTO existingUserWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        UserWeightDTO testExistingUserWeightDTO = userWeightService.save(existingUserWeightDTO);

        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeSave);
        assertThat(testExistingUserWeightDTO).isNotNull();
        assertThat(testExistingUserWeightDTO.getId()).isEqualTo(userWeight.getId());
    }

    @Test
    public void updateUserWeight() {
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
    public void findAllUserWeights() {
        int numberOfUserWeights = 10;
        List<UserWeight> userWeights = CreateEntities.generateUserWeights(em, numberOfUserWeights);
        int databaseSize = userWeightRepository.findAll().size();

        assertThat(databaseSize).isEqualTo(numberOfUserWeights);

        Page<UserWeightDTO> userWeightDTOsPage = userWeightService.findAll(null);

        assertThat(userWeightDTOsPage.getTotalElements()).isEqualTo(numberOfUserWeights);

        List<UserWeight> testUserWeights = userWeightMapper.userWeightDTOsToUserWeights(userWeightDTOsPage.getContent());
        for (UserWeight testUserWeight : testUserWeights)
        {
            assertThat(userWeights).contains(testUserWeight);
        }
    }

    @Test
    public void findAllUserWeightsByUserId() {
        int numberOfUserWeights = 10;
        List<UserWeight> userWeights = CreateEntities.generateUserWeightsForSingleUser(em, numberOfUserWeights);
        assertThat(userWeights.get(0)).isNotNull();
        User user = userWeights.get(0).getUserDemographic().getUser();
        assertThat(user).isNotNull();

        // create more user weights for another user
        CreateEntities.generateUserWeightsForSingleUser(em, numberOfUserWeights);

        Page<UserWeightDTO> userWeightDTOsPage = userWeightService.findAllByUserId(null,user.getId());

        assertThat(userWeightDTOsPage.getTotalElements()).isEqualTo(numberOfUserWeights);

        List<UserWeight> testUserWeights = userWeightMapper.userWeightDTOsToUserWeights(userWeightDTOsPage.getContent());
        for (UserWeight testUserWeight : testUserWeights)
        {
            assertThat(userWeights).contains(testUserWeight);
        }
    }

    @Test
    public void findOneUserWeightsById() {
        em.persist(userWeight);
        em.flush();

        UserWeight testUserWeight = userWeightMapper.userWeightDTOToUserWeight(userWeightService.findOne(userWeight.getId()));

        assertThat(testUserWeight).isNotNull();
        assertThat(testUserWeight.getId()).isEqualTo(userWeight.getId());
    }

    @Test
    public void deleteUserWeightsById() {
        em.persist(userWeight);
        em.flush();

        int databaseSizeBeforeDelete = userWeightRepository.findAll().size();

        userWeightService.delete(userWeight.getId());

        List<UserWeight> userWeights = userWeightRepository.findAll();
        assertThat(userWeights).hasSize(databaseSizeBeforeDelete - 1);
        assertThat(userWeights).doesNotContain(userWeight);
    }
}
