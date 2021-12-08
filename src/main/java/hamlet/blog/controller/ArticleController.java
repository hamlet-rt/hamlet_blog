package hamlet.blog.controller;

import hamlet.blog.entity.*;
import hamlet.blog.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private EntityManager manager;

    @GetMapping(path = "/create")
    public String showCreateArticlePage(Model model){
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        return "public/create_article_page";
    }


    @PostMapping(path = "/create")
    public String proceedArticleCreation(Authentication authentication,
            @RequestParam String heading,
            @RequestParam String text,
            @RequestParam Long category_id
    ){
        String username = authentication.getName();
        Article article = new Article();
        article.setHeading(heading);
        article.setText(text);
        article.setUser(userRepository.findByLogin(username).get());
        article.setCategory(categoryRepository.getById(category_id));
        article.setDate(LocalDate.now());
        article.setIsPublished(false);
        articleRepository.save(article);
        return "public/success_article";
    }

    @GetMapping(path = "/show/{id}")
    public String showArticlePage(@PathVariable Long id, Model model, Authentication authentication){
        Article article = articleRepository.findById(id).orElse(null);
        List<Rating> ratingList = article.getRatings();
        int sum = 0;
        for(Rating rating : ratingList){
            sum += rating.getRating();
        }
        if (ratingList.size() != 0){
            int averageRating = sum / ratingList.size();
            model.addAttribute("averageRating", averageRating);
        }else{
            model.addAttribute("averageRating", 0);
        }
        model.addAttribute("article", articleRepository.findById(id).orElse(null));
        if (authentication != null) {
            String username = authentication.getName();
            model.addAttribute("rating", ratingRepository.existsByUserAndArticle(userRepository.findByLogin(username).get(),
                    articleRepository.findById(id).get()));
        } else {
            model.addAttribute("rating", false);
        }
        return "public/show_article_page";
    }

    @GetMapping(path = "/show/best")
    public String bestArticlesPage(Model model){
        TypedQuery<Article> bestArticlesQuery = manager.createQuery(
          "select a from Article a join Rating r on a.id = r.article.id group by a.id order by avg(r.rating) desc", Article.class
        );
        List<Article> articleList = bestArticlesQuery.getResultList();
        model.addAttribute("articleList", articleList);
        return "public/best_articles_page";
    }

    @GetMapping(path = "/show/all")
    public String allArticlesPage(@RequestParam Optional<Boolean> articleStatus, Model model){
        List<Article> articleList;
        if (articleStatus.isPresent()){
            articleList = articleRepository.findAllByIsPublished(articleStatus.get());
        }else {
            articleList = articleRepository.findAll();
        }
        model.addAttribute("articleList", articleList);
        return "public/all_articles_page";
    }
}
