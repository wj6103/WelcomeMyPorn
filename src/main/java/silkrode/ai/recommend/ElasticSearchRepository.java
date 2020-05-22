package silkrode.ai.recommend;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchRepository extends ElasticsearchRepository<Movie,Long> {
}
