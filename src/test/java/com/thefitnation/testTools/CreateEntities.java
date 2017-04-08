package com.thefitnation.testTools;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.SkillLevel;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.domain.enumeration.Gender;
import com.thefitnation.domain.enumeration.UnitOfMeasure;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class CreateEntities {

    private static int uniqueUserNumber = 0;

    public static List<User> generateUniqueUsers(EntityManager em, int numberOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            users.add(generateUniqueUser(em));
        }
        return users;
    }

    public static User generateUniqueUser(EntityManager em) {
        User user = new User();
        user.setLogin("test" + uniqueUserNumber);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("test" + uniqueUserNumber + "@test.com");
        user.setFirstName("test" + uniqueUserNumber);
        user.setLastName("test" + uniqueUserNumber);
        user.setImageUrl("http://placehold.it/50x50");
        em.persist(user);
        em.flush();
        uniqueUserNumber++;
        return user;
    }

    public static UserDemographic generateUserDemographic(EntityManager em) {
        UserDemographic userDemographic = new UserDemographic()
            .createdOn(LocalDate.ofEpochDay(0L))
            .lastLogin(LocalDate.ofEpochDay(0L))
            .gender(Gender.Male)
            .dateOfBirth(LocalDate.ofEpochDay(0L))
            .height(1F)
            .unitOfMeasure(UnitOfMeasure.Imperial)
            .user(generateUniqueUser(em))
            .skillLevel(generateSkillLevel(em));
        em.persist(userDemographic);
        em.flush();
        return userDemographic;
    }

    public static SkillLevel generateSkillLevel(EntityManager em) {
        SkillLevel skillLevel = new SkillLevel()
            .level("Beginner");
        em.persist(skillLevel);
        em.flush();
        return skillLevel;
    }

    public static List<UserWeight> generateUserWeights(EntityManager em, int numberOfUserWeights) {
        List<UserWeight> userWeights = new ArrayList<>();
        for (int i = 0; i < numberOfUserWeights; i++) {
            userWeights.add(generateUserWeight(em));
        }
        return userWeights;
    }

    public static List<UserWeight> generateUserWeightsForSingleUser(EntityManager em, int numberOfUserWeights) {
        List<UserWeight> userWeights = new ArrayList<>();
        UserDemographic userDemographic = generateUserDemographic(em);
        for (int i = 0; i < numberOfUserWeights; i++) {
            userWeights.add(generateUserWeightForUser(em, userDemographic));
        }
        return userWeights;
    }

    public static UserWeight generateUserWeight(EntityManager em) {
        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(generateUserDemographic(em));
        em.persist(userWeight);
        em.flush();
        return userWeight;
    }

    public static UserWeight generateUserWeightForUser(EntityManager em, UserDemographic userDemographic) {
        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        em.persist(userWeight);
        em.flush();
        return userWeight;
    }
}
