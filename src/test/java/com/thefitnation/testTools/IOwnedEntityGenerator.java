package com.thefitnation.testTools;

import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;

import javax.persistence.EntityManager;
import java.util.List;

public interface IOwnedEntityGenerator<T> {
     T getOne(EntityManager entityManager);

     T getOne(EntityManager entityManager, User user);

     T getOne(EntityManager entityManager, String username, String password);

    List<T> getMany(EntityManager entityManager, int count);

    List<T> getMany(EntityManager entityManager, int count, User user);

    List<T> getMany(EntityManager entityManager, int count, String username, String password);
}
