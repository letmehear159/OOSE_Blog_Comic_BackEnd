package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.ReactionReq;
import OOSE_Final_Project.Blog.dto.res.ReactionRes;
import OOSE_Final_Project.Blog.dto.res.user.UserReactionRes;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.entity.reaction.ReactionBlog;
import OOSE_Final_Project.Blog.entity.reaction.ReactionComment;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {UserRepository.class, BlogRepository.class, CommentRepository.class})
public abstract class ReactionMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "blog", source = "blogId", qualifiedByName = "mapBlog")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateReactionBlogFromDto(ReactionReq source, @MappingTarget ReactionBlog target);

    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "comment", source = "commentId", qualifiedByName = "mapComment")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateReactionCommentFromDto(ReactionReq source, @MappingTarget ReactionComment target);


    @Mapping(target = "user", source = "author", qualifiedByName = "mapUser")
    @Mapping(target = "commentId", source = "comment", qualifiedByName = "mapCommentId")
    public abstract void updateReactionCommentResponseFromEntity(
            ReactionComment source,
            @MappingTarget ReactionRes target);

    @Mapping(target = "user", source = "author", qualifiedByName = "mapUser")
    @Mapping(target = "blogId", source = "blog", qualifiedByName = "mapBlogId")
    public abstract void updateReactionBlogResponseFromEntity(
            ReactionBlog source,
            @MappingTarget ReactionRes target);

    @Named("mapBlogId")
    long mapBlogId(Blog blog) {
        return blog.getId();
    }

    @Named("mapUser")
    UserReactionRes mapUser(User user) {
        return UserReactionRes.builder()
                              .userId(user.getId())
                              .displayName(user.getDisplayName())
                              .build();
    }

    @Named("mapCommentId")
    long mapCommentId(Comment comment) {
        return comment.getId();
    }


    @Named("mapAuthor")
    User mapAuthor(Long authorId) {
        if (authorId == null) {
            return null;
        }
        return userRepository.findById(authorId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorId));
    }

    @Named("mapBlog")
    Blog mapBlog(Long blogId) {
        if (blogId == null) {
            return null;
        }
        return blogRepository.findById(blogId)
                             .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));
    }

    @Named("mapComment")
    Comment mapComment(Long commentId) {
        if (commentId == null) {
            return null;
        }
        return commentRepository.findById(commentId)
                                .orElseThrow(
                                        () -> new IllegalArgumentException("Comment not found with id: " + commentId));
    }


}
