package com.thefitnation.repository;

import com.thefitnation.domain.Location;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Location entity.
 */
@SuppressWarnings("unused")
public interface LocationRepository extends JpaRepository<Location,Long> {

}
