package hamlet.blog.repository;

import hamlet.blog.entity.Article;
import hamlet.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByIsPublished(Boolean isPublished);
}
