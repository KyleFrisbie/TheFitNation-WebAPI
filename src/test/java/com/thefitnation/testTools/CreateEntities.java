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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
@Transactional
public class CreateEntities {

    private static int uniqueUserNumber = 0;

    public static List<User> generateUniqueUsers(int numberOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            users.add(generateUniqueUser());
        }
        return users;
    }

    public static User generateUniqueUser() {
        User user = new User();
        user.setLogin("test" + uniqueUserNumber);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("test" + uniqueUserNumber + "@test.com");
        user.setFirstName("test" + uniqueUserNumber);
        user.setLastName("test" + uniqueUserNumber);
        user.setImageUrl("http://placehold.it/50x50");
        uniqueUserNumber++;
        return user;
    }

    public static UserDemographic generateUserDemographics(User user, SkillLevel skillLevel) {
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

    public static SkillLevel generateSkillLevel(String skillLevelName) {
        return new SkillLevel()
            .level(skillLevelName);
    }

    public static List<UserWeight> generateUserWeights(UserDemographic userDemographic, int numberOfUserWeights) {
        List<UserWeight> userWeights = new ArrayList<>();
        for (int i = 0; i < numberOfUserWeights; i++) {
            userWeights.add(generateUserWeight(userDemographic));
        }
        return userWeights;
    }

    public static UserWeight generateUserWeight(UserDemographic userDemographic) {
        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }
}
