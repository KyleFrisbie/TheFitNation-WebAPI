package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the ExerciseInstance entity.
 */
@SuppressWarnings("unused")
public interface ExerciseInstanceRepository extends JpaRepository<ExerciseInstance,Long> {

    Page<ExerciseInstance> findByUserLoginOrderByDateDesc(String currentUserLogin, Pageable pageable);
}
