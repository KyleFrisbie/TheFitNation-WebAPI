package com.thefitnation.repository;

import com.thefitnation.domain.Gym;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gym entity.
 */
@SuppressWarnings("unused")
public interface GymRepository extends JpaRepository<Gym,Long> {

}
