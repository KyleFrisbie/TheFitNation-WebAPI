package com.thefitnation.repository.search;

import com.thefitnation.domain.UserExerciseSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UserExerciseSet entity.
 */
public interface UserExerciseSetSearchRepository extends ElasticsearchRepository<UserExerciseSet, Long> {
}
