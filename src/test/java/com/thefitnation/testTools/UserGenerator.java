package com.thefitnation.testTools;

import com.thefitnation.domain.User;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class UserGenerator {

    private static int uniqueUserNumber = 0;

    public static User getOne() {
        uniqueUserNumber++;
        return getOne("test" + uniqueUserNumber, RandomStringUtils.random(60));
    }

    public static User getOne(String username, String password) {
        User user = new User();
        user.setLogin(username);
        user.setPassword(password);
        user.setActivated(true);
        user.setEmail(username + "@test.com");
        user.setFirstName(username);
        user.setLastName(username);
        user.setImageUrl("http://placehold.it/50x50");
        return user;
    }

    public static List<User> getMany(EntityManager entityManager, int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = getOne();
            entityManager.persist(user);
            entityManager.flush();
            users.add(user);
        }
        return users;
    }
}
