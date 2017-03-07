package com.thefitnation.repository;

import com.thefitnation.domain.Unit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Unit entity.
 */
@SuppressWarnings("unused")
public interface UnitRepository extends JpaRepository<Unit,Long> {

}
