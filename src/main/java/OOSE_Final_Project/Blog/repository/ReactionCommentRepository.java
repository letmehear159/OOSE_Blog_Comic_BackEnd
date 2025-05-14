package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.reaction.ReactionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReactionCommentRepository extends JpaRepository<ReactionComment, Long> {


    List<ReactionComment> findAllByCommentId(Long id);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
