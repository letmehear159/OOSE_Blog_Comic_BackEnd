package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.ReactionReq;
import OOSE_Final_Project.Blog.entity.reaction.ReactionComment;

import java.util.List;

public interface IReactionCommentService {

    ReactionComment createReaction(ReactionReq reactionCommentReq);

    List<ReactionComment> getAllReactions();

    List<ReactionComment> getAllReactionsByCommentId(Long id);

    void deleteReaction(Long id);
}
