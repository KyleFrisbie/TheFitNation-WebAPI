package com.thefitnation.testTools;

import com.thefitnation.domain.UserDemographic;

import javax.persistence.EntityManager;
import java.util.List;

public interface IOwnedEntityGenerator<T> {
     T getOne(EntityManager entityManager);

     T getOne(EntityManager entityManager, UserDemographic userDemographic);

    List<T> getMany(EntityManager entityManager, int count);

    List<T> getMany(EntityManager entityManager, int count, UserDemographic userDemographic);
}
