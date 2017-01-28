package com.thefitnation.repository.search;

import com.thefitnation.domain.WorkoutTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the WorkoutTemplate entity.
 */
public interface WorkoutTemplateSearchRepository extends ElasticsearchRepository<WorkoutTemplate, Long> {
}
