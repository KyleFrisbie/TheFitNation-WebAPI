package com.thefitnation.repository.search;

import com.thefitnation.domain.Gym;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Gym entity.
 */
public interface GymSearchRepository extends ElasticsearchRepository<Gym, Long> {
}
