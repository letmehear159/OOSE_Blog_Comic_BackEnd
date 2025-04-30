package OOSE_Final_Project.Blog.service.strategy.reaction;

import OOSE_Final_Project.Blog.dto.req.ReactionReq;
import OOSE_Final_Project.Blog.dto.res.ReactionRes;
import OOSE_Final_Project.Blog.entity.reaction.ReactionComment;
import OOSE_Final_Project.Blog.enums.EReaction;
import OOSE_Final_Project.Blog.mapper.ReactionMapper;
import OOSE_Final_Project.Blog.repository.ReactionCommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("reactionCommentServiceImpl")
@Transactional
public class ReactionCommentServiceImpl implements IReactionCommentService {

    private final ReactionCommentRepository reactionCommentRepository;

    @Autowired
    ReactionMapper reactionMapper;

    public ReactionCommentServiceImpl(
            ReactionCommentRepository reactionCommentRepository
    ) {
        this.reactionCommentRepository = reactionCommentRepository;
    }

    @Override
    public ReactionRes createReaction(ReactionReq reactionReq) {
        ReactionComment reactionComment = new ReactionComment();

        reactionMapper.updateReactionCommentFromDto(reactionReq, reactionComment);
        // Kiểm tra reaction type không null
        if (reactionComment.getReaction() == null) {
            throw new IllegalArgumentException("Reaction type cannot be null");
        }
        reactionComment = reactionCommentRepository.save(reactionComment);
        return changeToResponse(reactionComment);
    }

    @Override
    public List<ReactionRes> getAllReactions() {
        var reactions = reactionCommentRepository.findAll();
        return changeToResponse(reactions);
    }

    @Override
    public List<ReactionRes> getAllReactionById(Long id) {
        var reactions = reactionCommentRepository.findAllByCommentId(id);
        return changeToResponse(reactions);
    }

    @Override
    public ReactionRes updateReaction(Long id, EReaction reaction) {
        var oldReaction = reactionCommentRepository.findById(id)
                                                   .orElseThrow(
                                                           () -> new IllegalArgumentException("Reaction not found"));
        if (oldReaction.getReaction() == reaction) {
            return changeToResponse(oldReaction);
        }
        oldReaction.setReaction(reaction);
        oldReaction = reactionCommentRepository.save(oldReaction);
        return changeToResponse(oldReaction);

    }

    @Override
    public void deleteReaction(Long id) {
        if (!reactionCommentRepository.existsById(id)) {
            throw new IllegalArgumentException("Reaction not found with id: " + id);
        }
        reactionCommentRepository.deleteById(id);
    }

    ReactionRes changeToResponse(ReactionComment reactionComment) {
        ReactionRes reactionRes = new ReactionRes();
        reactionMapper.updateReactionCommentResponseFromEntity(reactionComment, reactionRes);
        return reactionRes;
    }

    List<ReactionRes> changeToResponse(List<ReactionComment> reactionComments) {
        return reactionComments.stream()
                               .map(this::changeToResponse)
                               .collect(Collectors.toList());
    }
}
