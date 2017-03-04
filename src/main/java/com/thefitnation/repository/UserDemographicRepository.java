package com.thefitnation.repository;

import com.thefitnation.domain.UserDemographic;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserDemographic entity.
 */
@SuppressWarnings("unused")
public interface UserDemographicRepository extends JpaRepository<UserDemographic,Long> {

}
