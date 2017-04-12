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
            User user = generateUniqueUser();
            em.persist(user);
            em.flush();
            users.add(user);
        }
        return users;
    }

    public static User generateUniqueUser() {
        return generateUniqueUser("test" + uniqueUserNumber, RandomStringUtils.random(60));
    }

    public static User generateUniqueUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setActivated(true);
        user.setEmail(login + "@test.com");
        user.setFirstName(login);
        user.setLastName(login);
        user.setImageUrl("http://placehold.it/50x50");
        uniqueUserNumber++;
        return user;
    }

    public static List<UserDemographic> generateUserDemographics(EntityManager em, int numberOfUserDemographics) {
        List<UserDemographic> userDemographics = new ArrayList<>(numberOfUserDemographics);
        for (int i = 0; i < numberOfUserDemographics; i++) {
            UserDemographic userDemographic = generateUserDemographic(em);
            em.persist(userDemographic);
            em.flush();
            userDemographics.add(userDemographic);
        }
        return userDemographics;
    }

    public static List<UserDemographic> generateUserDemographics(EntityManager em, int numberOfUserDemographics, User user) {
        List<UserDemographic> userDemographics = new ArrayList<>(numberOfUserDemographics);
        for (int i = 0; i < numberOfUserDemographics; i++) {
            UserDemographic userDemographic = generateUserDemographic(em, user);
            em.persist(userDemographic);
            em.flush();
            userDemographics.add(userDemographic);
        }
        return userDemographics;
    }

    public static UserDemographic generateUserDemographic(EntityManager em) {
        User user = generateUniqueUser();
        em.persist(user);
        em.flush();

        SkillLevel skillLevel = generateSkillLevel();
        em.persist(skillLevel);
        em.flush();

        return new UserDemographic()
            .createdOn(LocalDate.ofEpochDay(0L))
            .lastLogin(LocalDate.ofEpochDay(0L))
            .gender(Gender.Male)
            .dateOfBirth(LocalDate.ofEpochDay(0L))
            .height(1F)
            .unitOfMeasure(UnitOfMeasure.Imperial)
            .user(user)
            .skillLevel(skillLevel);
    }

    public static UserDemographic generateUserDemographic(EntityManager em, String login, String password) {
        User user = generateUniqueUser(login, password);
        em.persist(user);
        em.flush();

        SkillLevel skillLevel = generateSkillLevel();
        em.persist(skillLevel);
        em.flush();

        return new UserDemographic()
            .createdOn(LocalDate.ofEpochDay(0L))
            .lastLogin(LocalDate.ofEpochDay(0L))
            .gender(Gender.Male)
            .dateOfBirth(LocalDate.ofEpochDay(0L))
            .height(1F)
            .unitOfMeasure(UnitOfMeasure.Imperial)
            .user(user)
            .skillLevel(skillLevel);
    }

    public static UserDemographic generateUserDemographic(EntityManager em, User user) {
        SkillLevel skillLevel = generateSkillLevel();
        em.persist(skillLevel);
        em.flush();

        return new UserDemographic()
            .createdOn(LocalDate.ofEpochDay(0L))
            .lastLogin(LocalDate.ofEpochDay(0L))
            .gender(Gender.Male)
            .dateOfBirth(LocalDate.ofEpochDay(0L))
            .height(1F)
            .unitOfMeasure(UnitOfMeasure.Imperial)
            .user(user)
            .skillLevel(skillLevel);
    }

    public static SkillLevel generateSkillLevel() {
        return new SkillLevel().level("Beginner");
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

    private static List<UserWeight> createUserWeightsForUserDemographic(EntityManager em, int numberOfUserWeights, UserDemographic userDemographic) {
        List<UserWeight> userWeights = new ArrayList<>();
        for (int i = 0; i < numberOfUserWeights; i++) {
            UserWeight userWeight = generateUserWeightForUser(userDemographic);
            em.persist(userWeight);
            em.flush();

            userWeights.add(userWeight);
        }
        return userWeights;
    }

    public static List<UserWeight> generateUserWeightsForUser(EntityManager em, int numberOfUserWeights, String login, String password) {
        UserDemographic userDemographic = generateUserDemographic(em, login, password);
        em.persist(userDemographic);
        em.flush();
        return createUserWeightsForUserDemographic(em, numberOfUserWeights, userDemographic);
    }

    public static List<UserWeight> generateUserWeightsForUser(EntityManager em, int numberOfUserWeights, User user) {
        UserDemographic userDemographic = generateUserDemographic(em, user);
        em.persist(userDemographic);
        em.flush();
        return createUserWeightsForUserDemographic(em, numberOfUserWeights, userDemographic);
    }

    public static UserWeight generateUserWeight(EntityManager em) {
        UserDemographic userDemographic = generateUserDemographic(em);
        em.persist(userDemographic);
        em.flush();

        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }

    public static UserWeight generateUserWeightForUser(EntityManager em, String login, String password) {
        UserDemographic userDemographic = generateUserDemographic(em, login, password);
        em.persist(userDemographic);
        em.flush();

        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }

    public static UserWeight generateUserWeightForUser(EntityManager em, User user) {
        UserDemographic userDemographic = generateUserDemographic(em, user);
        em.persist(userDemographic);
        em.flush();

        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }

    public static UserWeight generateUserWeightForUser(UserDemographic userDemographic) {
        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }

    public static UserWeight generateUserWeightForUser(EntityManager em) {
        UserDemographic userDemographic = generateUserDemographic(em);
        em.persist(userDemographic);
        em.flush();

        return generateUserWeightForUser(userDemographic);
    }

    public static Optional<User> logInUser(String login, String password, UserRepository userRepository) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(login, password));
        SecurityContextHolder.setContext(securityContext);

        return userRepository.findOneByLogin(login);
    }
}
