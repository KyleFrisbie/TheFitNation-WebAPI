package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.SkillLevel;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.domain.enumeration.Gender;
import com.thefitnation.domain.enumeration.UnitOfMeasure;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class UserWeightRepositoryIntTest {

    private static final int NUMBER_OF_USER_WEIGHTS = 10;
    private static final LocalDate DEFAULT_WEIGHT_DATE = LocalDate.ofEpochDay(0L);
    private static final Float DEFAULT_WEIGHT = 1F;

    private List<User> users = new ArrayList<User>();

    @Autowired
    private EntityManager em;

    @Autowired
    private UserWeightRepository userWeightRepository;

    @Before
    public void setup() {
        generateUsers();
        for (User user : users) {
            UserDemographic userDemographic = generateUserDemographics(user);
            generateUserWeights(userDemographic);
        }
    }

    private void generateUsers() {
        for (int i = 0; i < 2; i++) {
            User user = new User();
            user.setLogin("test" + i);
            user.setPassword(RandomStringUtils.random(60));
            user.setActivated(true);
            user.setEmail("test" + i + "@test.com");
            user.setFirstName("test" + i);
            user.setLastName("test" + i);
            user.setImageUrl("http://placehold.it/50x50");
            user.setLangKey("en");
            em.persist(user);
            em.flush();
            users.add(user);
        }
    }

    private UserDemographic generateUserDemographics(User user) {
        UserDemographic userDemographic = new UserDemographic()
            .createdOn(LocalDate.ofEpochDay(0L))
            .lastLogin(LocalDate.ofEpochDay(0L))
            .gender(Gender.Male)
            .dateOfBirth(LocalDate.ofEpochDay(0L))
            .height(1F)
            .unitOfMeasure(UnitOfMeasure.Imperial)
            .user(user);
        SkillLevel skillLevel = new SkillLevel().level("AAAAAAAAAA");
        em.persist(skillLevel);
        em.flush();
        userDemographic.setSkillLevel(skillLevel);
        em.persist(userDemographic);
        return userDemographic;
    }

    private void generateUserWeights(UserDemographic userDemographic) {
        for (int i = 0; i < NUMBER_OF_USER_WEIGHTS; i++) {
            UserWeight userWeight = new UserWeight()
                .weightDate(DEFAULT_WEIGHT_DATE)
                .weight(DEFAULT_WEIGHT);
            userWeight.setUserDemographic(userDemographic);
            em.persist(userWeight);
        }
    }

    @Test
    public void findAllByUserId() {
        User user = users.get(0);
        Page<UserWeight> userWeights = userWeightRepository.findAllByUserId(null, user.getId());

        assertThat(userWeights.getTotalElements()).isEqualTo(NUMBER_OF_USER_WEIGHTS);
        for (UserWeight userWeight : userWeights.map(uw -> uw)) {
            assertThat(userWeight.getUserDemographic().getUser().getId()).isEqualTo(user.getId());
        }
    }
}
