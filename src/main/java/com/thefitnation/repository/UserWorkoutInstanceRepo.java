package com.thefitnation.repository;

import com.thefitnation.model.*;
import org.springframework.data.repository.*;

/**
 * Created by michael on 2/24/17.
 */
public interface UserWorkoutInstanceRepo extends PagingAndSortingRepository<UserWorkoutInstance, Long> {
}
