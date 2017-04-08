package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.SkillLevel;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.testTools.CreateEntities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class UserWeightRepositoryIntTest {

    private static final int NUMBER_OF_USER_WEIGHTS = 10;

    private List<User> users = new ArrayList<User>();

    @Autowired
    private EntityManager em;

    @Autowired
    private UserWeightRepository userWeightRepository;

    @Before
    public void setup() {
        users = CreateEntities.generateUniqueUsers(2);
        for (User user : users) {
            em.persist(user);
            em.flush();

            SkillLevel skillLevel = CreateEntities.generateSkillLevel("Beginner");
            em.persist(skillLevel);
            em.flush();

            UserDemographic userDemographic = CreateEntities.generateUserDemographics(user, skillLevel);
            em.persist(userDemographic);
            em.flush();

            List<UserWeight> userWeights = CreateEntities.generateUserWeights(userDemographic, NUMBER_OF_USER_WEIGHTS);
            for (UserWeight userWeight : userWeights) {
                em.persist(userWeight);
                em.flush();
            }
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
