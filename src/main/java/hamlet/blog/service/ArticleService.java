package hamlet.blog.service;

import hamlet.blog.entity.Article;
import hamlet.blog.entity.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    public double getAverageRating(Article article){
        List<Rating> ratingList = article.getRatings();
        int sum = 0;
        for(Rating rating: ratingList){
            sum += rating.getRating();
        }
        if (ratingList.size() != 0){
            return sum / ratingList.size();
        }else{
            return 0;
        }
    }
}
