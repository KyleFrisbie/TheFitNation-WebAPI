package com.thefitnation.repository;

import com.thefitnation.domain.UserWeight;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserWeight entity.
 */
@SuppressWarnings("unused")
public interface UserWeightRepository extends JpaRepository<UserWeight,Long> {

}
