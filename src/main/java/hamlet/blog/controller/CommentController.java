package hamlet.blog.controller;


import hamlet.blog.entity.Comment;
import hamlet.blog.repository.ArticleRepository;
import hamlet.blog.repository.CommentRepository;
import hamlet.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping(path = "/comment")
public class CommentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;


    @PostMapping(path = "/add")
    public String addCommentPage(Authentication authentication,
                                 @RequestParam String text,
                                 @RequestParam Long article_id) {
        String username = authentication.getName();
        Comment comment = new Comment();
        comment.setUser(userRepository.findByLogin(username).orElse(null));
        comment.setArticle(articleRepository.findById(article_id).get());
        comment.setText(text);
        comment.setDate(LocalDate.now());
        commentRepository.save(comment);
        return "redirect:/articles/show/" + article_id;
    }
}
