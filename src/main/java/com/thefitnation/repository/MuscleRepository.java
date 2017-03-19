package com.thefitnation.repository;

import com.thefitnation.domain.Muscle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Muscle entity.
 */
@SuppressWarnings("unused")
public interface MuscleRepository extends JpaRepository<Muscle,Long> {

   Muscle findByNameIgnoreCase(String Name);
}
