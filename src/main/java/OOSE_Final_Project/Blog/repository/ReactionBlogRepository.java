package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.reaction.ReactionBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionBlogRepository extends JpaRepository<ReactionBlog, Long> {


    Optional<ReactionBlog> findByAuthorIdAndBlogId(Long id, Long id1);

    List<ReactionBlog> findByBlogId(Long blogId);

    List<ReactionBlog> findAllByBlogId(Long id);
}

