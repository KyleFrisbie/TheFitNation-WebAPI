package com.thefitnation.repository.search;

import com.thefitnation.domain.UserWorkoutTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UserWorkoutTemplate entity.
 */
public interface UserWorkoutTemplateSearchRepository extends ElasticsearchRepository<UserWorkoutTemplate, Long> {
}
