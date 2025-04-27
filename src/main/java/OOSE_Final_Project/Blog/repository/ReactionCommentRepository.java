package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.Blog;
import OOSE_Final_Project.Blog.entity.ReactionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionCommentRepository extends JpaRepository<ReactionComment, Long> {


}
