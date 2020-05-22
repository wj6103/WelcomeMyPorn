package silkrode.ai.recommend;

import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
public class RecommendController {

    private final RecommendationService recommendationService;

    public RecommendController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }
    @GetMapping("/watch")
    public Object GetWatch20() throws ParseException {
        return recommendationService.NormalRecommend("watch");
    }

    @GetMapping("/watch/month")
    public Object GetMonthWatch20() throws ParseException {
        return recommendationService.NormalRecommend("Monthwatch");
    }

    @GetMapping("/like")
    public Object GetLike20() throws ParseException {
        return recommendationService.NormalRecommend("like");
    }

    @GetMapping("/like/month")
    public Object GetMonthLike20() throws ParseException {
        return recommendationService.NormalRecommend("Monthlike");
    }

    @GetMapping("/categories/{category}")
    public Object GetCategories20(@PathVariable("category") String category){
        return recommendationService.CategoryRecommend(category);
    }

    @GetMapping("/recommend/{id}")
    public Object Recommend(@PathVariable("id") String id)  {
        return recommendationService.ContentRecommend(id);
    }

    @GetMapping("/search/{keyword}")
    public Object Search(@PathVariable("keyword") String keyword){
        return recommendationService.SearchFilm(keyword);
    }

    @GetMapping("/get/{id}")
    public Object SearchSpecific(@PathVariable("id") String id){
        return recommendationService.SearchSpecificFilm(id);
    }

    @PostMapping("/rating")
    public void SetRating(@RequestBody Rating rating) {
        recommendationService.SetRating(rating);
    }
    @GetMapping("/getRating/{user}/{movie}")
    public Object GetRating(@PathVariable("user") Long userId,@PathVariable("movie") Long movieId){
        return recommendationService.GetRating(userId,movieId);
    }
}
