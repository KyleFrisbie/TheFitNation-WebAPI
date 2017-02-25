package com.thefitnation.repository;

import com.thefitnation.model.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Created by michael on 2/24/17.
 */
@Repository
public interface MuscleRepo extends PagingAndSortingRepository<Muscle, Long> {

}
