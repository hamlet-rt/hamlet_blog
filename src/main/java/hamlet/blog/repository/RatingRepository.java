package hamlet.blog.repository;

import hamlet.blog.entity.Article;
import hamlet.blog.entity.Rating;
import hamlet.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    boolean existsByUserAndArticle(User user, Article article);
}
