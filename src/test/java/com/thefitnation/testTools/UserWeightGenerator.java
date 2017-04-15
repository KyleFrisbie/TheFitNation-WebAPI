package com.thefitnation.testTools;

import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWeight;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserWeightGenerator implements IOwnedEntityGenerator<UserWeight> {

    private static UserWeightGenerator instance;

    private UserWeightGenerator() {
    }

    public static UserWeightGenerator getInstance() {
        if (instance == null) {
            instance = new UserWeightGenerator();
        }
        return instance;
    }

    @Override
    public UserWeight getOne(EntityManager entityManager) {
        UserDemographic userDemographic = UserDemographicGenerator.getInstance().getOne(entityManager);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }

    @Override
    public UserWeight getOne(EntityManager entityManager, User user) {
        UserDemographic userDemographic = UserDemographicGenerator.getInstance().getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }

    @Override
    public UserWeight getOne(EntityManager entityManager, String username, String password) {
        UserDemographic userDemographic = UserDemographicGenerator.getInstance().getOne(entityManager, username, password);
        entityManager.persist(userDemographic);
        entityManager.flush();

        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }

    @Override
    public List<UserWeight> getMany(EntityManager entityManager, int count) {
        List<UserWeight> userWeights = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserWeight userWeight = getOne(entityManager);
            entityManager.persist(userWeight);
            entityManager.flush();
            userWeights.add(userWeight);
        }
        return userWeights;
    }

    @Override
    public List<UserWeight> getMany(EntityManager entityManager, int count, User user) {
        UserDemographic userDemographic = UserDemographicGenerator.getInstance().getOne(entityManager, user);
        entityManager.persist(userDemographic);
        entityManager.flush();
        return createUserWeightsForUserDemographic(entityManager, count, userDemographic);
    }

    @Override
    public List<UserWeight> getMany(EntityManager entityManager, int count, String username, String password) {
        UserDemographic userDemographic = UserDemographicGenerator.getInstance().getOne(entityManager, username, password);
        entityManager.persist(userDemographic);
        entityManager.flush();
        return createUserWeightsForUserDemographic(entityManager, count, userDemographic);
    }

    private List<UserWeight> createUserWeightsForUserDemographic(EntityManager em, int numberOfUserWeights, UserDemographic userDemographic) {
        List<UserWeight> userWeights = new ArrayList<>();
        for (int i = 0; i < numberOfUserWeights; i++) {
            UserWeight userWeight = getManyForUserDemographic(userDemographic);
            em.persist(userWeight);
            em.flush();

            userWeights.add(userWeight);
        }
        return userWeights;
    }

    public UserWeight getManyForUserDemographic(UserDemographic userDemographic) {
        UserWeight userWeight = new UserWeight()
            .weightDate(LocalDate.ofEpochDay(0L))
            .weight(1F);
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }
}
