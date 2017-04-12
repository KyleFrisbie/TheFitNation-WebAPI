package com.thefitnation.service;


import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.enumeration.Gender;
import com.thefitnation.domain.enumeration.UnitOfMeasure;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.service.dto.UserDemographicDTO;
import com.thefitnation.service.mapper.UserDemographicMapper;
import com.thefitnation.service.mapper.UserDemographicMapper;
import com.thefitnation.testTools.TestUtils;
import com.thefitnation.web.rest.UserDemographicResourceIntTest;
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
public class UserDemographicServiceIntTest {

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_LOGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_LOGIN = LocalDate.now(ZoneId.systemDefault());

    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_HEIGHT = 1F;
    private static final Float UPDATED_HEIGHT = 2F;

    private static final UnitOfMeasure DEFAULT_UNIT_OF_MEASURE = UnitOfMeasure.Imperial;
    private static final UnitOfMeasure UPDATED_UNIT_OF_MEASURE = UnitOfMeasure.Metric;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDemographicService userDemographicService;

    @Autowired
    private UserDemographicRepository userDemographicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDemographicMapper userDemographicMapper;

    @Test
    public void saveNewUserDemographicAsLoggedInUser() {
        Optional<User> user = TestUtils.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em, user.get());

        int databaseSizeBeforeCreate = userDemographicRepository.findAll().size();

        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        UserDemographicDTO testUserDemographicDTO = userDemographicService.save(userDemographicDTO);

        int databaseSizeAfterCreate = userDemographicRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
        assertThat(testUserDemographicDTO.getId()).isNotNull();
        assertThat(testUserDemographicDTO.getUserId()).isEqualTo(user.get().getId());
    }

    @Test
    public void saveNewUserDemographicNotOwnedByLoggedInUser() {
        Optional<User> user = TestUtils.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em);
        em.persist(userDemographic);
        em.flush();

        int databaseSizeBeforeUpdate = userDemographicRepository.findAll().size();
        UserDemographic userOwnedUserDemographic = userDemographicRepository.findOneByUserWithEagerRelationships(user.get().getId());
        assertThat(userOwnedUserDemographic).isNull();

        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        UserDemographicDTO testUserDemographicDTO = userDemographicService.save(userDemographicDTO);

        int databaseSizeAfterCreate = userDemographicRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeUpdate + 1);
        assertThat(testUserDemographicDTO.getId()).isNotEqualTo(userDemographic.getId());
        assertThat(testUserDemographicDTO.getUserId()).isEqualTo(user.get().getId());
    }

    @Test
    public void saveNewUserDemographicWithoutValidLogin() {
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em);

        int databaseSizeBeforeCreate = userDemographicRepository.findAll().size();

        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        UserDemographicDTO testUserDemographicDTO = userDemographicService.save(userDemographicDTO);

        int databaseSizeAfterCreate = userDemographicRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate);
        assertThat(testUserDemographicDTO).isNull();
    }

    @Test
    public void saveNewUserDemographicAsAdmin() {
        Optional<User> user = TestUtils.logInUser("admin", "admin", userRepository);
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em);

        int databaseSizeBeforeUpdate = userDemographicRepository.findAll().size();

        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        UserDemographicDTO testUserDemographicDTO = userDemographicService.save(userDemographicDTO);

        int databaseSizeAfterCreate = userDemographicRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeUpdate + 1);
        assertThat(testUserDemographicDTO.getId()).isNotNull();
        assertThat(testUserDemographicDTO.getUserId()).isNotEqualTo(user.get().getId());
    }

    @Test
    public void updateUserDemographicForUser() {
        Optional<User> user = TestUtils.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em, user.get());
        em.persist(userDemographic);
        em.flush();

        userDemographic.setGender(UPDATED_GENDER);
        userDemographic.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        userDemographic.setHeight(UPDATED_HEIGHT);
        userDemographic.setUnitOfMeasure(UPDATED_UNIT_OF_MEASURE);

        int databaseSizeBeforeUpdate = userDemographicRepository.findAll().size();

        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        UserDemographicDTO testUserDemographicDTO = userDemographicService.save(userDemographicDTO);

        int databaseSizeAfterCreate = userDemographicRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeUpdate);
        assertThat(testUserDemographicDTO.getId()).isNotNull();
        assertThat(testUserDemographicDTO.getId()).isEqualTo(userDemographic.getId());
        assertThat(testUserDemographicDTO.getUserId()).isEqualTo(user.get().getId());
        assertThat(testUserDemographicDTO.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserDemographicDTO.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testUserDemographicDTO.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testUserDemographicDTO.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    public void findAllUserDemographicsForAdmin() {
        TestUtils.logInUser("admin", "admin", userRepository);
        int numberOfUserDemographics = 10;
        List<UserDemographic> originalUserDemographics = TestUtils.generateUserDemographics(em, numberOfUserDemographics);

        Page<UserDemographicDTO> testUserDemographicDTOs = userDemographicService.findAll(null);

        assertThat(testUserDemographicDTOs.getTotalElements()).isEqualTo(numberOfUserDemographics);
        List<UserDemographic> testUserDemographics = userDemographicMapper.userDemographicDTOsToUserDemographics(testUserDemographicDTOs.getContent());
        for (UserDemographic userDemographic : testUserDemographics) {
            assertThat(originalUserDemographics).contains(userDemographic);
        }
    }

    @Test
    public void findAllUserDemographicsForUser() {
        TestUtils.logInUser("user", "user", userRepository);
        int numberOfUserDemographics = 10;
        TestUtils.generateUserDemographics(em, numberOfUserDemographics);

        Page<UserDemographicDTO> testUserDemographicDTOs = userDemographicService.findAll(null);

        assertThat(testUserDemographicDTOs).isNull();
    }

    @Test
    public void findOneUserDemographicsById() {
        Optional<User> user = TestUtils.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em, user.get());
        em.persist(userDemographic);
        em.flush();

        UserDemographicDTO testUserDemographicDTO = userDemographicService.findOne(userDemographic.getId());
        assertThat(testUserDemographicDTO).isNotNull();
        assertThat(testUserDemographicDTO.getId()).isEqualTo(userDemographic.getId());
        assertThat(testUserDemographicDTO.getUserId()).isEqualTo(user.get().getId());
    }

    @Test
    public void findOneUserDemographicsByIdNotOwnedByUser() {
        Optional<User> user = TestUtils.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em);
        em.persist(userDemographic);
        em.flush();

        UserDemographicDTO testUserDemographicDTO = userDemographicService.findOne(userDemographic.getId());
        assertThat(testUserDemographicDTO).isNull();
    }

    @Test
    public void findOneUserDemographicsByIdNotOwnedByAdmin() {
        Optional<User> user = TestUtils.logInUser("admin", "admin", userRepository);
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em);
        em.persist(userDemographic);
        em.flush();

        UserDemographicDTO testUserDemographicDTO = userDemographicService.findOne(userDemographic.getId());
        assertThat(testUserDemographicDTO).isNotNull();
        assertThat(testUserDemographicDTO.getId()).isEqualTo(userDemographic.getId());
        assertThat(testUserDemographicDTO.getUserId()).isNotEqualTo(user.get().getId());
    }

    @Test
    public void deleteUserDemographicsById() {
        Optional<User> user = TestUtils.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em, user.get());
        em.persist(userDemographic);
        em.flush();

        int databaseSizeBeforeDelete = userDemographicRepository.findAll().size();
        userDemographicService.delete(userDemographic.getId());
        assertThat(userDemographicRepository.findAll().size()).isEqualTo(databaseSizeBeforeDelete);
    }

    @Test
    public void deleteOneUserDemographicsAsAdmin() {
        Optional<User> user = TestUtils.logInUser("admin", "admin", userRepository);
        UserDemographic userDemographic = TestUtils.generateUserDemographic(em, user.get());
        em.persist(userDemographic);
        em.flush();

        int databaseSizeBeforeDelete = userDemographicRepository.findAll().size();
        userDemographicService.delete(userDemographic.getId());
        assertThat(userDemographicRepository.findAll().size()).isEqualTo(databaseSizeBeforeDelete - 1);
    }
}
