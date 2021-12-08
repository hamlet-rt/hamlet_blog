package hamlet.blog.controller;

import hamlet.blog.entity.Article;
import hamlet.blog.entity.User;
import hamlet.blog.entity.UserRole;
import hamlet.blog.repository.ArticleRepository;
import hamlet.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin")
public class AdministratorController {

    @Autowired
    private EntityManager manager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping(path = "/articles")
    public String adminAllArticlesPage(Model model){
        TypedQuery<Article> allArticlesQuery = manager.createQuery(
                "select a from Article a group by a.id", Article.class
        );
        List<Article> articleList = allArticlesQuery.getResultList();
        model.addAttribute("articleList", articleList);
        return "public/admin_all_articles_page";
    }

    @GetMapping(path = "/article_list")
    public String showArticles(@RequestParam Optional<Boolean> articleStatus, Model model) {
        List<Article> articleList;
        if (articleStatus.isPresent()) {
            articleList = articleRepository.findAllByIsPublished(articleStatus.get());
        } else {
            articleList = articleRepository.findAll();
        }
        model.addAttribute("articleList", articleList);
        return "/public/admin_all_articles_page";
    }



    @PostMapping(path = "/add")
    public String isPublished(
                              @RequestParam Long article_id,
                              @RequestParam boolean isPublished){
        Article article = articleRepository.findById(article_id).get();
        article.setIsPublished(isPublished);
        articleRepository.save(article);
        return "redirect:/admin/articles";
    }

    @GetMapping (path = "/users")
    public String adminRolesPage(@RequestParam Optional<Long> userRoleId, Model model){
        TypedQuery<UserRole> userRoleQuery = manager.createQuery(
                "select u from UserRole u group by u.id", UserRole.class
        );
        List<UserRole> userRoleList = userRoleQuery.getResultList();
        List<User> userList;
        if(userRoleId.isPresent()){
            userList = userRepository.findAllByUserRoleId(userRoleId.get());
        }else{
            userList = userRepository.findAll();
        }
        model.addAttribute("userRoleList", userRoleList);
        model.addAttribute("userList", userList);
        return "public/admin_all_users_page";
    }

    @PostMapping (path = "/add_role")
    public String roleSelection(
            @RequestParam Long user_id,
            @RequestParam UserRole user_role
    ){
        User user = userRepository.findById(user_id).get();
        user.setUserRole(user_role);
        userRepository.save(user);
        return "redirect:/admin/users";
    }
}
