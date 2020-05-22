package silkrode.ai.recommend;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RatingRepository extends ElasticsearchRepository<Rating,Long> {
}
