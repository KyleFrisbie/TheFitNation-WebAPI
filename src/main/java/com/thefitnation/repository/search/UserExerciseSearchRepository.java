package com.thefitnation.repository.search;

import com.thefitnation.domain.UserExercise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UserExercise entity.
 */
public interface UserExerciseSearchRepository extends ElasticsearchRepository<UserExercise, Long> {
}
