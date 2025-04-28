package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.ReactionReq;
import OOSE_Final_Project.Blog.entity.reaction.ReactionComment;
import OOSE_Final_Project.Blog.mapper.ReactionMapper;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.repository.ReactionCommentRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.IReactionCommentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReactionCommentServiceImpl implements IReactionCommentService {

    private final ReactionCommentRepository reactionCommentRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    @Autowired
    ReactionMapper reactionMapper;

    public ReactionCommentServiceImpl(
            ReactionCommentRepository reactionCommentRepository,
            CommentRepository commentRepository,
            UserRepository userRepository) {
        this.reactionCommentRepository = reactionCommentRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReactionComment createReaction(ReactionReq reactionReq) {
        ReactionComment reactionComment = new ReactionComment();

        reactionMapper.updateReactionCommentFromDto(reactionReq, reactionComment);
        // Kiểm tra reaction type không null
        if (reactionComment.getReaction() == null) {
            throw new IllegalArgumentException("Reaction type cannot be null");
        }

        // Kiểm tra xem user đã có reaction cho blog này chưa
        Optional<ReactionComment> oldReaction = reactionCommentRepository.findByAuthorIdAndCommentId(
                reactionComment.getAuthor()
                               .getId(), reactionComment.getComment()
                                                        .getId());

        if (oldReaction.isEmpty()) {
            return reactionCommentRepository.save(reactionComment);
        }
        var reaction = oldReaction.get();
        if (reaction.getReaction()
                    .equals(reactionComment.getReaction())) {
            throw new IllegalArgumentException("Reaction cannot be the same");
        }
        reactionCommentRepository.delete(reaction);
        return reactionCommentRepository.save(reactionComment);
    }

    @Override
    public List<ReactionComment> getAllReactions() {
        return reactionCommentRepository.findAll();
    }

    @Override
    public List<ReactionComment> getAllReactionsByCommentId(Long id) {
        return reactionCommentRepository.findAllByCommentId(id);
    }


    @Override
    public void deleteReaction(Long id) {
        if (!reactionCommentRepository.existsById(id)) {
            throw new IllegalArgumentException("Reaction not found with id: " + id);
        }
        reactionCommentRepository.deleteById(id);
    }
}
