package com.thefitnation.repository.search;

import com.thefitnation.domain.WorkoutLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the WorkoutLog entity.
 */
public interface WorkoutLogSearchRepository extends ElasticsearchRepository<WorkoutLog, Long> {
}
