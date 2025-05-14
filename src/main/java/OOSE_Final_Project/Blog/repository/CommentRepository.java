package OOSE_Final_Project.Blog.repository;
import OOSE_Final_Project.Blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<OOSE_Final_Project.Blog.entity.Comment, Long> {


    List<Comment> findByBlogId(Long blogId);

    List<Comment> findByAuthorId(Long userId);

    List<Comment> findByParentId(Long parentId);

    List<Comment> findByParentIsNullAndBlogId(Long blogId);
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
