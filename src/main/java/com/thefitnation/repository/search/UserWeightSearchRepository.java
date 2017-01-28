package com.thefitnation.repository.search;

import com.thefitnation.domain.UserWeight;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UserWeight entity.
 */
public interface UserWeightSearchRepository extends ElasticsearchRepository<UserWeight, Long> {
}
