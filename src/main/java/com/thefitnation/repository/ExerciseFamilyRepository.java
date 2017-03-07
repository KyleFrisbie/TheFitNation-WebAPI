package com.thefitnation.repository;

import com.thefitnation.domain.ExerciseFamily;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExerciseFamily entity.
 */
@SuppressWarnings("unused")
public interface ExerciseFamilyRepository extends JpaRepository<ExerciseFamily,Long> {

}
