package com.thefitnation.repository;

import com.thefitnation.domain.WorkoutInstance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkoutInstance entity.
 */
@SuppressWarnings("unused")
public interface WorkoutInstanceRepository extends JpaRepository<WorkoutInstance,Long> {

}
