package com.thefitnation.testTools;

import com.thefitnation.domain.SkillLevel;

public class SkillLevelGenerator implements IUnownedEntityGenerator<SkillLevel> {

    private static SkillLevelGenerator instance;

    private SkillLevelGenerator() {}

    public static SkillLevelGenerator getInstance() {
        if(instance == null) {
            instance = new SkillLevelGenerator();
        }
        return instance;
    }

    @Override
    public SkillLevel getOne() {
        return new SkillLevel().level("Beginner");
    }
}
