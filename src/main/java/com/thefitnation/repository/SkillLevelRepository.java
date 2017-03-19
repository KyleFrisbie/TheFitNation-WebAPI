package com.thefitnation.repository;

import com.thefitnation.domain.SkillLevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SkillLevel entity.
 */
@SuppressWarnings("unused")
public interface SkillLevelRepository extends JpaRepository<SkillLevel,Long> {
    @Query("select skillLevel from SkillLevel skillLevel where skillLevel.level =:level")
    SkillLevel findOneByLevel(@Param("level") String level);
}
