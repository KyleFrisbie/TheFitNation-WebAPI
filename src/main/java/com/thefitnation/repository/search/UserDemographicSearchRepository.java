package com.thefitnation.repository.search;

import com.thefitnation.domain.UserDemographic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UserDemographic entity.
 */
public interface UserDemographicSearchRepository extends ElasticsearchRepository<UserDemographic, Long> {
}
