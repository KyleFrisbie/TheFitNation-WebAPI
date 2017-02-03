package com.thefitnation.repository;

import com.thefitnation.domain.WorkoutTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkoutTemplate entity.
 */
@SuppressWarnings("unused")
public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate,Long> {

}
