package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.reaction.ReactionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionCommentRepository extends JpaRepository<ReactionComment, Long> {


    List<ReactionComment> findAllByCommentId(Long id);

    Optional<ReactionComment> findByAuthorIdAndCommentId(long attr0, long attr1);
}
