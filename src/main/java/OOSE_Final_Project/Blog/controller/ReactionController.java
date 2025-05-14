package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.ReactionReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.ReactionRes;
import OOSE_Final_Project.Blog.enums.EReaction;
import OOSE_Final_Project.Blog.observer.ReactionPublisher;
import OOSE_Final_Project.Blog.service.strategy.reaction.IReactionService;
import OOSE_Final_Project.Blog.service.strategy.reaction.ReactionStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reactions")
public class ReactionController {

    @Autowired
    ReactionStrategyFactory reactionStrategyFactory;

    @Autowired
    ReactionPublisher reactionPublisher;

    @PostMapping("")
    public ApiResponse<ReactionRes> createReaction(@RequestBody ReactionReq req) {
        IReactionService reactionService = reactionStrategyFactory.getStrategy(req.getType());
        ReactionRes res = reactionService.createReaction(req);
        reactionPublisher.notifyObservers(res);
        return (new ApiResponse<>(HttpStatus.OK, "Reaction created", res, null));
    }

    @GetMapping("")
    public ApiResponse<List<ReactionRes>> getAllBlogReactions(
            @RequestParam("type") String type
    ) {
        IReactionService reactionService = reactionStrategyFactory.getStrategy(String.valueOf(type));
        List<ReactionRes> res = reactionService.getAllReactions();
        return (new ApiResponse<>(HttpStatus.OK, "All " + type + " reactions", res, null));
    }


    @PutMapping("")
    public ApiResponse<ReactionRes> updateReaction(
            @RequestParam("type") String type,
            @RequestParam("id") Long id,
            @RequestParam("reaction") EReaction reaction) {

        IReactionService reactionService = reactionStrategyFactory.getStrategy(String.valueOf(type));
        ReactionRes res = reactionService.updateReaction(id, reaction);
        return (new ApiResponse<>(HttpStatus.OK, "Reaction updated", res, null));
    }

    @DeleteMapping("")
    public ApiResponse<Boolean> deleteReaction(
            @RequestParam("type") String type,
            @RequestParam("id") Long id) {
        IReactionService reactionService = reactionStrategyFactory.getStrategy(type);
        reactionService.deleteReaction(id);
        return (new ApiResponse<>(HttpStatus.OK, "Reaction deleted", Boolean.TRUE, null));
    }

    @GetMapping("count-all")
    public ApiResponse<Long> countAllReactions() {
        IReactionService reactionService = reactionStrategyFactory.getStrategy("Blog");
        long total = 0;
        total += reactionService.getAllReactions()
                                .size();
        reactionService = reactionStrategyFactory.getStrategy("Comment");
        total += reactionService.getAllReactions()
                                .size();
        return (new ApiResponse<>(HttpStatus.OK, "Total reactions", total, null));
    }
}
