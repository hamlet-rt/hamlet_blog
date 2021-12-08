package hamlet.blog.controller;

import hamlet.blog.entity.Rating;
import hamlet.blog.repository.ArticleRepository;
import hamlet.blog.repository.RatingRepository;
import hamlet.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/ratings")
public class RatingController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RatingRepository ratingRepository;


    @PostMapping(path = "/add")
    private String addRatingPage(Authentication authentication,
            @RequestParam Integer rating,
            @RequestParam Long article_id                     ){
        String login = authentication.getName();
        Rating ratingValue = new Rating();
        ratingValue.setUser(userRepository.findByLogin(login).orElse(null));
        ratingValue.setArticle(articleRepository.findById(article_id).get());
        ratingValue.setRating(rating);
        ratingRepository.save(ratingValue);
        return "redirect:/articles/show/" + article_id;
    }
}
