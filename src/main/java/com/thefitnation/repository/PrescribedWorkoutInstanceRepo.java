package com.thefitnation.repository;

import com.thefitnation.model.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Created by michael on 2/24/17.
 */
/**
 * <p></p>
 * @author michael menard
 * @version 0.0.1
 * @since 2/24/17.
 */
@Repository
public interface PrescribedWorkoutInstanceRepo extends PagingAndSortingRepository<PrescribedWorkoutInstance, Long> {
}
