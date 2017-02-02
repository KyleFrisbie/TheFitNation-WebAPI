package com.thefitnation.repository;

import com.thefitnation.domain.UserWorkoutTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserWorkoutTemplate entity.
 */
@SuppressWarnings("unused")
public interface UserWorkoutTemplateRepository extends JpaRepository<UserWorkoutTemplate,Long> {

}
