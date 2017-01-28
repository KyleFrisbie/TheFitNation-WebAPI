package com.thefitnation.repository.search;

import com.thefitnation.domain.Exercise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Exercise entity.
 */
public interface ExerciseSearchRepository extends ElasticsearchRepository<Exercise, Long> {
}
