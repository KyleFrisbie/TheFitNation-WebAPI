package com.thefitnation.repository;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the WorkoutTemplate entity.
 */
@SuppressWarnings("unused")
public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate,Long> {

    @Query("select workoutTemplate " +
            "from WorkoutTemplate workoutTemplate " +
            "where workoutTemplate.userDemographic.user.login = ?#{principal.username}")
    Page<WorkoutTemplateDTO> findAllByCurrentLoggedInUser(Pageable pageable);
}
