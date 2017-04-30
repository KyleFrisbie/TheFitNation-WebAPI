package com.thefitnation.testTools;

import com.thefitnation.domain.Unit;

import javax.persistence.EntityManager;

/**
 * Created by kylel on 4/15/2017.
 */
public class UnitGenerator implements IUnownedEntityGenerator<Unit> {

    private static int NAME_COUNTER = 0;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";

    private static UnitGenerator instance;

    private UnitGenerator() {}

    public static UnitGenerator getInstance() {
        if (instance == null) {
            instance = new UnitGenerator();
        }
        return instance;
    }
    @Override
    public Unit getOne(EntityManager entityManager) {
        return new Unit()
            .name(DEFAULT_NAME + NAME_COUNTER++);
    }
}
