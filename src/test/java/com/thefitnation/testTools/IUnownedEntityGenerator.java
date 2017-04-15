package com.thefitnation.testTools;


import javax.persistence.EntityManager;

public interface IUnownedEntityGenerator <T> {
    T getOne(EntityManager entityManager);
}
