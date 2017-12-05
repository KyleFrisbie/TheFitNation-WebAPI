package com.thefitnation.testTools;

import com.thefitnation.domain.BodyPart;

import javax.persistence.EntityManager;

/**
 * Created by kylel on 4/15/2017.
 */
public class BodyPartGenerator implements IUnownedEntityGenerator<BodyPart> {

    private static int NAME_COUNTER = 0;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";

    private static BodyPartGenerator instance;

    private BodyPartGenerator() {}

    public static BodyPartGenerator getInstance() {
        if(instance == null) {
            instance = new BodyPartGenerator();
        }
        return instance;
    }

    @Override
    public BodyPart getOne(EntityManager entityManager) {
        return new BodyPart()
            .name(DEFAULT_NAME + NAME_COUNTER++);
    }
}
