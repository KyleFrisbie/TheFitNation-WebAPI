package com.thefitnation.testTools;

import com.thefitnation.domain.SkillLevel;

import javax.persistence.EntityManager;

public class SkillLevelGenerator implements IUnownedEntityGenerator<SkillLevel> {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";

    private static SkillLevelGenerator instance;

    private SkillLevelGenerator() {}

    public static SkillLevelGenerator getInstance() {
        if(instance == null) {
            instance = new SkillLevelGenerator();
        }
        return instance;
    }

    @Override
    public SkillLevel getOne(EntityManager entityManager) {
        return new SkillLevel().level(DEFAULT_NAME);
    }
}
