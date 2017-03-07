package com.thefitnation.repository;

import com.thefitnation.domain.SkillLevel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SkillLevel entity.
 */
@SuppressWarnings("unused")
public interface SkillLevelRepository extends JpaRepository<SkillLevel,Long> {

}
