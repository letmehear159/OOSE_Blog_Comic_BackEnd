package OOSE_Final_Project.Blog.service.strategy.reaction;

import OOSE_Final_Project.Blog.dto.req.ReactionReq;
import OOSE_Final_Project.Blog.dto.res.ReactionRes;
import OOSE_Final_Project.Blog.entity.reaction.ReactionBlog;
import OOSE_Final_Project.Blog.enums.EReaction;
import OOSE_Final_Project.Blog.mapper.ReactionMapper;
import OOSE_Final_Project.Blog.repository.ReactionBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("reactionBlogServiceImpl")
public class ReactionBlogServiceImpl implements IReactionBlogService {

    @Autowired
    ReactionBlogRepository reactionBlogRepository;

    @Autowired
    private ReactionMapper reactionMapper;

    @Override
    public ReactionRes createReaction(ReactionReq reactionReq) {

        ReactionBlog reactionBlog = new ReactionBlog();
        reactionMapper.updateReactionBlogFromDto(reactionReq, reactionBlog);

        // Kiểm tra reaction type không null
        if (reactionBlog.getReaction() == null) {
            throw new IllegalArgumentException("Reaction type cannot be null");
        }

        reactionBlog = reactionBlogRepository.save(reactionBlog);
        return changeToResponse(reactionBlog);
    }

    @Override
    public List<ReactionRes> getAllReactions() {
        var reactions = reactionBlogRepository.findAll();
        return changeToResponse(reactions);
    }

    @Override
    public List<ReactionRes> getAllReactionById(Long id) {
        var reactions = reactionBlogRepository.findAllByBlogId(id);
        return changeToResponse(reactions);
    }

    @Override
    public ReactionRes updateReaction(Long id, EReaction reaction) {
        var oldReaction = reactionBlogRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Reaction not found"));
        if (oldReaction.getReaction() == reaction) {
            return changeToResponse(oldReaction);
        }
        oldReaction.setReaction(reaction);
        oldReaction = reactionBlogRepository.save(oldReaction);
        return changeToResponse(oldReaction);
    }

    @Override
    public void deleteReaction(Long id) {
        if (!reactionBlogRepository.existsById(id)) {
            throw new IllegalArgumentException("Reaction not found with id: " + id);
        }
        reactionBlogRepository.deleteById(id);
    }

    ReactionRes changeToResponse(ReactionBlog reactionBlog) {
        ReactionRes reactionRes = new ReactionRes();
        reactionMapper.updateReactionBlogResponseFromEntity(reactionBlog, reactionRes);
        return reactionRes;
    }

    List<ReactionRes> changeToResponse(List<ReactionBlog> reactionBlogs) {
        return reactionBlogs.stream()
                .map(this::changeToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public long getAllReaction() {
        return reactionBlogRepository.count();
    }

}
