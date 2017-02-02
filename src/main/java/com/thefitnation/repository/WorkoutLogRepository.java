package com.thefitnation.repository;

import com.thefitnation.domain.WorkoutLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkoutLog entity.
 */
@SuppressWarnings("unused")
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog,Long> {

}
