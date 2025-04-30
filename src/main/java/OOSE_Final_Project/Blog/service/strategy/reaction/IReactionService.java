package OOSE_Final_Project.Blog.service.strategy.reaction;

import OOSE_Final_Project.Blog.dto.req.ReactionReq;
import OOSE_Final_Project.Blog.dto.res.ReactionRes;
import OOSE_Final_Project.Blog.enums.EReaction;

import java.util.List;

public interface IReactionService {

    ReactionRes createReaction(ReactionReq reactionReq);

    List<ReactionRes> getAllReactions();

    List<ReactionRes> getAllReactionById(Long id);

    ReactionRes updateReaction(Long id, EReaction reaction);

    void deleteReaction(Long id);
}
