package com.thefitnation.testTools;

import com.thefitnation.domain.SkillLevel;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.enumeration.Gender;
import com.thefitnation.domain.enumeration.UnitOfMeasure;
import com.thefitnation.repository.UserDemographicRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDemographicGenerator implements IOwnedEntityGenerator<UserDemographic> {

    private static UserDemographicGenerator instance;

    private UserDemographicGenerator() {}

    public static UserDemographicGenerator getInstance() {
        if (instance == null) {
            instance = new UserDemographicGenerator();
        }
        return instance;
    }

    private UserDemographic createUserDemographic(EntityManager entityManager) {
        User user = UserGenerator.getOne();
        entityManager.persist(user);
        entityManager.flush();

        SkillLevel skillLevel = SkillLevelGenerator.getInstance().getOne(entityManager);
        entityManager.persist(skillLevel);
        entityManager.flush();

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

    private UserDemographic createUserDemographic(EntityManager entityManager, User user) {
        Query query = entityManager.createQuery("select userDemographic from UserDemographic userDemographic where userDemographic.user.id =:id");
        query.setParameter("id", user.getId());

        UserDemographic existingUserDemographic = (UserDemographic)query.getSingleResult();

        if (existingUserDemographic != null) {
            return existingUserDemographic;
        }

        SkillLevel skillLevel = SkillLevelGenerator.getInstance().getOne(entityManager);
        entityManager.persist(skillLevel);
        entityManager.flush();

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

    @Override
    public UserDemographic getOne(EntityManager entityManager) {
        return createUserDemographic(entityManager);
    }

    @Override
    public UserDemographic getOne(EntityManager entityManager, User user) {
        return createUserDemographic(entityManager, user);
    }

    @Override
    public UserDemographic getOne(EntityManager entityManager, String username, String password) {
        User user = UserGenerator.getOne(username, password);
        entityManager.persist(user);
        entityManager.flush();

        return createUserDemographic(entityManager, user);
    }

    @Override
    public List<UserDemographic> getMany(EntityManager entityManager, int count) {
        List<UserDemographic> userDemographics = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserDemographic userDemographic = getOne(entityManager);
            entityManager.persist(userDemographic);
            entityManager.flush();
            userDemographics.add(userDemographic);
        }
        return userDemographics;
    }

    @Override
    public List<UserDemographic> getMany(EntityManager entityManager, int count, User user) {
        List<UserDemographic> userDemographics = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserDemographic userDemographic = getOne(entityManager, user);
            entityManager.persist(userDemographic);
            entityManager.flush();
            userDemographics.add(userDemographic);
        }
        return userDemographics;
    }

    @Override
    public List<UserDemographic> getMany(EntityManager entityManager, int count, String username, String password) {
        User user = UserGenerator.getOne(username, password);
        return getMany(entityManager, count, user);
    }
}
