package com.thefitnation.repository;

import com.thefitnation.domain.BodyPart;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BodyPart entity.
 */
@SuppressWarnings("unused")
public interface BodyPartRepository extends JpaRepository<BodyPart,Long> {

}
