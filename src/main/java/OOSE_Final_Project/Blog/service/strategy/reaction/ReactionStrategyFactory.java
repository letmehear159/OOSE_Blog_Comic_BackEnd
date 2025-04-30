package OOSE_Final_Project.Blog.service.strategy.reaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReactionStrategyFactory {

    private final Map<String, IReactionService> strategies;

    @Autowired
    public ReactionStrategyFactory(
            @Qualifier("reactionBlogServiceImpl") IReactionService blog,
            @Qualifier("reactionCommentServiceImpl") IReactionService comment
    ) {
        strategies = new HashMap<>();
        strategies.put("Blog", blog);
        strategies.put("Comment", comment);
    }

    public IReactionService getStrategy(String type) {
        return strategies.get(type);
    }
}
