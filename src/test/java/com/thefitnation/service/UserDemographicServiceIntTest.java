package com.thefitnation.service;


import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserDemographic;
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

        int databaseSizeBeforeCreate = userDemographicRepository.findAll().size();

        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        UserDemographicDTO testUserDemographicDTO = userDemographicService.save(userDemographicDTO);

        int databaseSizeAfterCreate = userDemographicRepository.findAll().size();
        assertThat(databaseSizeAfterCreate).isEqualTo(databaseSizeBeforeCreate);
        assertThat(testUserDemographicDTO.getId()).isNull();
    }

    @Test
    public void saveNewUserDemographicWithoutValidLogin() {
    }

    @Test
    public void saveNewUserDemographicAsAdmin() {
    }

    @Test
    public void updateUserDemographicForUser() {
    }

    @Test
    public void findAllUserDemographicsForUser() {
    }

    @Test
    public void findAllUserDemographicsForAdmin() {
    }

    @Test
    public void findAllUserDemographicsForInvalidUser() {
    }

    @Test
    public void findOneUserDemographicsById() {
    }

    @Test
    public void findOneUserDemographicsByIdNotOwnedByUser() {
    }

    @Test
    public void findOneUserDemographicsByIdNotOwnedByAdmin() {
    }

    @Test
    public void deleteOneUserDemographicsById() {
    }

    @Test
    public void deleteOneUserDemographicsByIdNotOwnedByUser() {
    }

    @Test
    public void deleteOneUserDemographicsByIdNotOwnedByAdmin() {
}


}
