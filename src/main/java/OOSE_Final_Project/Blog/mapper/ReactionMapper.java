package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.ReactionReq;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.entity.reaction.ReactionBlog;
import OOSE_Final_Project.Blog.entity.reaction.ReactionComment;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
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
    public abstract void updateReactionBlogFromDto(ReactionReq source, @MappingTarget ReactionBlog target);

    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "comment", source = "commentId", qualifiedByName = "mapComment")
    public abstract void updateReactionCommentFromDto(ReactionReq source, @MappingTarget ReactionComment target);

    @Named("mapAuthor")
    User mapAuthor(Long authorId) {
        return userRepository.findById(authorId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorId));
    }

    @Named("mapBlog")
    Blog mapBlog(Long blogId) {
        return blogRepository.findById(blogId)
                             .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));
    }

    @Named("mapComment")
    Comment mapComment(Long commentId) {
        return commentRepository.findById(commentId)
                                .orElseThrow(
                                        () -> new IllegalArgumentException("Comment not found with id: " + commentId));
    }


}
