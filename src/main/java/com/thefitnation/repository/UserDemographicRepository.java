package com.thefitnation.repository;

import com.thefitnation.domain.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the UserDemographic entity.
 */
@SuppressWarnings("unused")
public interface UserDemographicRepository extends JpaRepository<UserDemographic,Long> {

    @Query("select distinct userDemographic from UserDemographic userDemographic left join fetch userDemographic.gyms")
    List<UserDemographic> findAllWithEagerRelationships();

    @Query("select userDemographic from UserDemographic userDemographic left join fetch userDemographic.gyms where userDemographic.id =:id")
    UserDemographic findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select userDemographic from UserDemographic userDemographic left join fetch userDemographic.gyms where userDemographic.user.id =:id")
    UserDemographic findOneByUserWithEagerRelationships(@Param("id") Long id);

    @Query("select userDemographic from UserDemographic userDemographic where userDemographic.user.id =:id")
    UserDemographic findOneByUserId(@Param(value = "id") Long id);

    @Query("select userDemographic from UserDemographic userDemographic where userDemographic.user.login = :login")
    UserDemographic findOneByLogin(@Param(value = "login") String currentUserLogin);
}
