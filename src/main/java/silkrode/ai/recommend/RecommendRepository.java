package silkrode.ai.recommend;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Slf4j
public class RecommendRepository {

    private final ElasticSearchRepository elasticSearchRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final RatingRepository ratingRepository;
    public RecommendRepository(ElasticSearchRepository elasticSearchRepository, ElasticsearchTemplate elasticsearchTemplate, RatingRepository ratingRepository) {
        this.elasticSearchRepository = elasticSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.ratingRepository = ratingRepository;
    }

    public Object GetLikeTop20(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        SourceFilter sourceFilter = new FetchSourceFilter(new String[]{"id","title","video_url","like","image"},null);
        queryBuilder.withPageable(PageRequest.of(0,100,Sort.by("like").descending()));
        queryBuilder.withSourceFilter(sourceFilter);
        return elasticSearchRepository.search(queryBuilder.build());
    }
    public Object GetMonthLikeTop20() throws ParseException {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //過濾sim
        SourceFilter sourceFilter = new FetchSourceFilter(new String[]{"id","title","video_url","like","image"},null);
        queryBuilder.withSourceFilter(sourceFilter);
        //取時間範圍
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String currentTime = formatter.format(date);
        String firstDay = currentTime.substring(0,5) + "01-01" + currentTime.substring(10);
        System.out.println(currentTime +" , "+ firstDay);
        queryBuilder.withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("published_time").gte(firstDay).lte(currentTime)
                        .includeLower(true).includeUpper(true)));
        //依like排序
        queryBuilder.withPageable(PageRequest.of(0,100,Sort.by("like").descending()));
        return elasticSearchRepository.search(queryBuilder.build());
    }
    public Object GetWatchTop20(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        SourceFilter sourceFilter = new FetchSourceFilter(new String[]{"id","title","video_url","watch","image"},null);
        queryBuilder.withPageable(PageRequest.of(0,100,Sort.by("watch").descending()));
        queryBuilder.withSourceFilter(sourceFilter);
        return elasticSearchRepository.search(queryBuilder.build());
    }
    public Object GetMonthWatchTop20() throws ParseException {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //過濾sim
        SourceFilter sourceFilter = new FetchSourceFilter(new String[]{"id","title","video_url","watch","image"},null);
        queryBuilder.withSourceFilter(sourceFilter);
        //依like排序
        queryBuilder.withPageable(PageRequest.of(0,100,Sort.by("watch").descending()));
        //取時間範圍
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String currentTime = formatter.format(date);
        String firstDay = currentTime.toString().substring(0,5) + "01-01" + currentTime.toString().substring(10);
        queryBuilder.withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("published_time").gte(firstDay).lte(currentTime)
                        .includeLower(true).includeUpper(true)));
        return elasticSearchRepository.search(queryBuilder.build());
    }
    public Object GetCategoriesTop20(String category){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        SourceFilter sourceFilter = new FetchSourceFilter(new String[]{"id","title","video_url","watch","like","published_time","image"},null);
        queryBuilder.withQuery(QueryBuilders.matchQuery("categories.keyword",category));
        queryBuilder.withPageable(PageRequest.of(0,100,Sort.by("watch").descending()));
        queryBuilder.withSourceFilter(sourceFilter);
        return elasticSearchRepository.search(queryBuilder.build());
    }
    public Object GetSimilarity(String id){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("id",id));
        Page<Movie> r = elasticSearchRepository.search(queryBuilder.build());
        Movie m = r.iterator().next();
        SourceFilter sourceFilter = new FetchSourceFilter(new String[]{"id","title","video_url","watch","like","published_time","image"},null);
        queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.termsQuery("id",m.sim));
        return elasticSearchRepository.search(queryBuilder.build());
    }
    public Object GetFilm(String keyword){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("title",keyword));
        queryBuilder.withPageable(PageRequest.of(0,100));
        return elasticSearchRepository.search(queryBuilder.build());
    }
    public Object GetSpecificFilm(String id){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("id",id));
        return elasticSearchRepository.search(queryBuilder.build());
    }
    public void SetRating(Rating rating) {
        elasticsearchTemplate.createIndex(Rating.class);
        elasticsearchTemplate.putMapping(Rating.class);
        ratingRepository.save(rating);
    }
    public Object GetRating(Long userId, Long movieId){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("userId", userId))
                .must(QueryBuilders.matchQuery("movieId", movieId))
        );
        return ratingRepository.search(queryBuilder.build());
    }
}
