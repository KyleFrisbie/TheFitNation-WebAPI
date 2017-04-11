package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.testTools.TestUtils;
import org.junit.Before;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class UserWeightRepositoryIntTest {

    private static final int NUMBER_OF_USER_WEIGHTS = 10;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserWeightRepository userWeightRepository;

    @Test
    public void findAllByUserId() {
        User user = TestUtils.generateUniqueUser(em);
        List<UserWeight> userWeights = TestUtils.generateUserWeightsForUser(em, NUMBER_OF_USER_WEIGHTS, user);
        Page<UserWeight> savedUserWeights = userWeightRepository.findAllByUserId(null, user.getId());

        assertThat(savedUserWeights.getTotalElements()).isEqualTo(NUMBER_OF_USER_WEIGHTS);
        for (UserWeight userWeight : savedUserWeights.map(uw -> uw)) {
            assertThat(userWeight.getUserDemographic().getUser().getId()).isEqualTo(user.getId());
        }
    }

    @Test
    public void findOneByUserId() {
        User user = TestUtils.generateUniqueUser(em);
        List<UserWeight> userWeights = TestUtils.generateUserWeightsForUser(em, NUMBER_OF_USER_WEIGHTS, user);
        UserWeight userWeight = userWeights.get(0);
        Optional<UserWeight> foundUserWeight = userWeightRepository.findOneByUserId(userWeight.getId(), user.getId());
        assertThat(foundUserWeight.isPresent());
        assertThat(foundUserWeight.get().getUserDemographic().getId()).isEqualTo(userWeight.getUserDemographic().getId());
    }
}
