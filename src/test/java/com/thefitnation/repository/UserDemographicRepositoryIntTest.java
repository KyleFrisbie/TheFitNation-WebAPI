package com.thefitnation.repository;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class UserDemographicRepositoryIntTest {

    private static final int NUMBER_OF_USER_DEMOGRAPHICS = 10;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDemographicRepository userDemographicRepository;

    @Test
    public void findAllWithEagerRelationships() {
        UserDemographicGenerator.getMany(em, NUMBER_OF_USER_DEMOGRAPHICS);
        List<UserDemographic> savedUserDemographics = userDemographicRepository.findAllWithEagerRelationships();

        assertThat(savedUserDemographics.size()).isEqualTo(NUMBER_OF_USER_DEMOGRAPHICS);
    }

    @Test
    public void findOneWithEagerRelationships() {
        List<UserDemographic> savedUserDemographics = UserDemographicGenerator.getMany(em, NUMBER_OF_USER_DEMOGRAPHICS);
        UserDemographic userDemographic = savedUserDemographics.get(0);
        UserDemographic foundUserDemographic = userDemographicRepository.findOne(userDemographic.getId());
        assertThat(foundUserDemographic).isNotNull();
    }

    @Test
    public void findOneByUserWithEagerRelationships() {
        User user = UserGenerator.getOne();
        em.persist(user);
        em.flush();

        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user);
        em.persist(userDemographic);
        em.flush();

        UserDemographic foundUserDemographic = userDemographicRepository.findOneByUserWithEagerRelationships(user.getId());
        assertThat(foundUserDemographic).isNotNull();
    }
}
