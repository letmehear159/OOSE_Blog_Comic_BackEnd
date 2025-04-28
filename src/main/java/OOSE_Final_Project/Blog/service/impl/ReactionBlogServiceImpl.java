package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.ReactionReq;
import OOSE_Final_Project.Blog.entity.reaction.ReactionBlog;
import OOSE_Final_Project.Blog.mapper.ReactionMapper;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.ReactionBlogRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.IReactionBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionBlogServiceImpl implements IReactionBlogService {

    private final ReactionBlogRepository reactionBlogRepository;

    private final UserRepository userRepository;

    private final BlogRepository blogRepository;

    @Autowired
    private ReactionMapper reactionMapper;

    @Autowired
    public ReactionBlogServiceImpl(
            ReactionBlogRepository reactionBlogRepository,
            UserRepository userRepository,
            BlogRepository blogRepository) {
        this.reactionBlogRepository = reactionBlogRepository;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
    }


    @Override
    public ReactionBlog createReactionBlog(ReactionReq reactionReq) {

        ReactionBlog reactionBlog = new ReactionBlog();
        reactionMapper.updateReactionBlogFromDto(reactionReq, reactionBlog);

        // Kiểm tra reaction type không null
        if (reactionBlog.getReaction() == null) {
            throw new IllegalArgumentException("Reaction type cannot be null");
        }

        // Kiểm tra xem user đã có reaction cho blog này chưa
        Optional<ReactionBlog> oldReaction = reactionBlogRepository.findByAuthorIdAndBlogId(
                reactionBlog.getAuthor()
                            .getId(), reactionBlog.getBlog()
                                                  .getId());

        if (oldReaction.isEmpty()) {
            return reactionBlogRepository.save(reactionBlog);
        }
        var reaction = oldReaction.get();
        if (reaction.getReaction()
                    .equals(reactionBlog.getReaction())) {
            throw new IllegalArgumentException("Reaction cannot be the same");
        }
        reactionBlogRepository.delete(reaction);
        return reactionBlogRepository.save(reactionBlog);

    }

    @Override
    public List<ReactionBlog> getReactionBlogsByBlogId(Long blogId) {
        return reactionBlogRepository.findByBlogId(blogId);
    }

    @Override
    public ReactionBlog updateReactionBlog(Long id, ReactionBlog reactionBlogDetails) {
//        ReactionBlog reactionBlog = reactionBlogRepository.findById(id)
//                                                          .orElseThrow(() -> new IllegalArgumentException(
//                                                                  "ReactionBlog not found with id: " + id));
//
//        // Cập nhật reaction type nếu không null
//        if (reactionBlogDetails.getReaction() != null) {
//            reactionBlog.setReaction(reactionBlogDetails.getReaction());
//        }
//
//        // Không cho phép thay đổi author hoặc blog để đảm bảo tính nhất quán
//        return reactionBlogRepository.save(reactionBlog);
        return null;
    }

    @Override
    public void deleteReactionBlog(Long id) {
        if (!reactionBlogRepository.existsById(id)) {
            throw new IllegalArgumentException("ReactionBlog not found with id: " + id);
        }
        reactionBlogRepository.deleteById(id);
    }
}
