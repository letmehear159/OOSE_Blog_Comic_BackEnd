package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.ReactionReq;
import OOSE_Final_Project.Blog.entity.reaction.ReactionBlog;

import java.util.List;

public interface IReactionBlogService {
    ReactionBlog createReactionBlog(ReactionReq reactionBlog);

    List<ReactionBlog> getReactionBlogsByBlogId(Long blogId);

    ReactionBlog updateReactionBlog(Long id, ReactionBlog reactionBlogDetails);

    void deleteReactionBlog(Long id);
}
