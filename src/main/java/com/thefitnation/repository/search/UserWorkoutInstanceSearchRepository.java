package com.thefitnation.repository.search;

import com.thefitnation.domain.UserWorkoutInstance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UserWorkoutInstance entity.
 */
public interface UserWorkoutInstanceSearchRepository extends ElasticsearchRepository<UserWorkoutInstance, Long> {
}
