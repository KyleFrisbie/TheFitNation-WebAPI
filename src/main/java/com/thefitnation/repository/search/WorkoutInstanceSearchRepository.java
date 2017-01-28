package com.thefitnation.repository.search;

import com.thefitnation.domain.WorkoutInstance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the WorkoutInstance entity.
 */
public interface WorkoutInstanceSearchRepository extends ElasticsearchRepository<WorkoutInstance, Long> {
}
