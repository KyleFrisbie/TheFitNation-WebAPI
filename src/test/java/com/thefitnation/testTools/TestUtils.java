package com.thefitnation.testTools;

import com.thefitnation.TheFitNationApp;
import com.thefitnation.domain.SkillLevel;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.domain.enumeration.Gender;
import com.thefitnation.domain.enumeration.UnitOfMeasure;
import com.thefitnation.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class TestUtils {

    private static int uniqueUserNumber = 0;

    public static List<User> generateUniqueUsers(EntityManager em, int numberOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            users.add(generateUniqueUser(em));
        }
        return users;
    }

    public static User generateUniqueUser(EntityManager em) {
        return generateUniqueUser(em, "test" + uniqueUserNumber, RandomStringUtils.random(60));
    }

    public static User generateUniqueUser(EntityManager em, String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setActivated(true);
        user.setEmail(login + "@test.com");
        user.setFirstName(login);
        user.setLastName(login);
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

    public static UserDemographic generateUserDemographic(EntityManager em, String login, String password) {
        UserDemographic userDemographic = new UserDemographic()
            .createdOn(LocalDate.ofEpochDay(0L))
            .lastLogin(LocalDate.ofEpochDay(0L))
            .gender(Gender.Male)
            .dateOfBirth(LocalDate.ofEpochDay(0L))
            .height(1F)
            .unitOfMeasure(UnitOfMeasure.Imperial)
            .user(generateUniqueUser(em, login, password))
            .skillLevel(generateSkillLevel(em));
        em.persist(userDemographic);
        em.flush();
        return userDemographic;
    }

    public static UserDemographic generateUserDemographic(EntityManager em, User user) {
        UserDemographic userDemographic = new UserDemographic()
            .createdOn(LocalDate.ofEpochDay(0L))
            .lastLogin(LocalDate.ofEpochDay(0L))
            .gender(Gender.Male)
            .dateOfBirth(LocalDate.ofEpochDay(0L))
            .height(1F)
            .unitOfMeasure(UnitOfMeasure.Imperial)
            .user(user)
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
            UserWeight userWeight = generateUserWeightForUser(em);
            em.persist(userWeight);
            em.flush();
            userWeights.add(generateUserWeight(em));
        }
        return userWeights;
    }

    public static List<UserWeight> generateUserWeightsForUser(EntityManager em, int numberOfUserWeights, String login, String password) {
        List<UserWeight> userWeights = new ArrayList<>();
        UserDemographic userDemographic = generateUserDemographic(em, login, password);
        for (int i = 0; i < numberOfUserWeights; i++) {
            UserWeight userWeight = generateUserWeightForUser(em, userDemographic);
            em.persist(userWeight);
            em.flush();
            userWeights.add(generateUserWeightForUser(em, userDemographic));
        }
        return userWeights;
    }

    public static List<UserWeight> generateUserWeightsForUser(EntityManager em, int numberOfUserWeights, User user) {
        List<UserWeight> userWeights = new ArrayList<>();
        UserDemographic userDemographic = generateUserDemographic(em, user);
        for (int i = 0; i < numberOfUserWeights; i++) {
            UserWeight userWeight = generateUserWeightForUser(em, userDemographic);
            em.persist(userWeight);
            em.flush();
            userWeights.add(userWeight);
        }
        return userWeights;
    }

    public static UserWeight generateUserWeight(EntityManager em) {
        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(generateUserDemographic(em));
        return userWeight;
    }

    public static UserWeight generateUserWeightForUser(EntityManager em, String login, String password) {
        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(generateUserDemographic(em, login, password));
        return userWeight;
    }

    public static UserWeight generateUserWeightForUser(EntityManager em, User user) {
        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(generateUserDemographic(em, user));
        return userWeight;
    }

    public static UserWeight generateUserWeightForUser(EntityManager em, UserDemographic userDemographic) {
        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }

    public static UserWeight generateUserWeightForUser(EntityManager em) {
        return generateUserWeightForUser(em, generateUserDemographic(em));
    }



    public static Optional<User> logInUser(String login, String password, UserRepository userRepository) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(login, password));
        SecurityContextHolder.setContext(securityContext);

        return userRepository.findOneByLogin(login);
    }
}
