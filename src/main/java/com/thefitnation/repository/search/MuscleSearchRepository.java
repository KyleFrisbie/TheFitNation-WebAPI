package com.thefitnation.repository.search;

import com.thefitnation.domain.Muscle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Muscle entity.
 */
public interface MuscleSearchRepository extends ElasticsearchRepository<Muscle, Long> {
}
