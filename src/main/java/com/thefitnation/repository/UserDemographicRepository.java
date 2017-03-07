package com.thefitnation.repository;

import com.thefitnation.domain.UserDemographic;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserDemographic entity.
 */
@SuppressWarnings("unused")
public interface UserDemographicRepository extends JpaRepository<UserDemographic,Long> {

    @Query("select distinct userDemographic from UserDemographic userDemographic left join fetch userDemographic.gyms")
    List<UserDemographic> findAllWithEagerRelationships();

    @Query("select userDemographic from UserDemographic userDemographic left join fetch userDemographic.gyms where userDemographic.id =:id")
    UserDemographic findOneWithEagerRelationships(@Param("id") Long id);

}
