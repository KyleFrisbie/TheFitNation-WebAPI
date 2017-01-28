package com.thefitnation.repository.search;

import com.thefitnation.domain.ExerciseSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ExerciseSet entity.
 */
public interface ExerciseSetSearchRepository extends ElasticsearchRepository<ExerciseSet, Long> {
}
