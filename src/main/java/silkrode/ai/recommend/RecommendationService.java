package silkrode.ai.recommend;

import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class RecommendationService {

    private final RecommendRepository recommendRepository;

    public RecommendationService(RecommendRepository recommendRepository) {
        this.recommendRepository = recommendRepository;
    }

    public Object NormalRecommend(String recommendType) throws ParseException {
        if(recommendType.equals("like"))
            return recommendRepository.GetLikeTop20();
        else if(recommendType.equals("Monthlike"))
            return recommendRepository.GetMonthLikeTop20();
        else if(recommendType.equals("month"))
            return recommendRepository.GetWatchTop20();
        else
            return recommendRepository.GetMonthWatchTop20();
    }
    public Object CategoryRecommend(String category){
        return recommendRepository.GetCategoriesTop20(category);
    }
    public Object ContentRecommend(String id){
        return recommendRepository.GetSimilarity(id);
    }
    public Object SearchFilm(String keyword)
    {
        return recommendRepository.GetFilm(keyword);
    }
    public Object SearchSpecificFilm(String id)
    {
        return recommendRepository.GetSpecificFilm(id);
    }
    public void SetRating(Rating rating){recommendRepository.SetRating(rating);}
    public Object GetRating(Long userId,Long movieId){return recommendRepository.GetRating(userId,movieId);}
}
