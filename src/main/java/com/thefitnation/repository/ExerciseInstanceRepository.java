package com.thefitnation.repository;

import com.thefitnation.domain.ExerciseInstance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExerciseInstance entity.
 */
@SuppressWarnings("unused")
public interface ExerciseInstanceRepository extends JpaRepository<ExerciseInstance,Long> {

}
