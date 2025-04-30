package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.CommentReq;
import OOSE_Final_Project.Blog.dto.res.comment.CommentRes;
import OOSE_Final_Project.Blog.dto.res.user.UserCommentRes;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {UserRepository.class, BlogRepository.class, CommentRepository.class})
public abstract class CommentMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Mapping(target = "author", source = "userId", qualifiedByName = "mapAuthor")
    @Mapping(target = "blog", source = "blogId", qualifiedByName = "mapBlog")
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "mapCommentParent")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateCommentFromDto(CommentReq source, @MappingTarget Comment target);

    @Mapping(target = "userCommentResponse", source = "author", qualifiedByName = "mapCommentResponse")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "hasChildComment", source = "children", qualifiedByName = "hasChildren")
    public abstract void updateCommentResponseFromEntity(Comment source, @MappingTarget CommentRes target);

    @Named("mapCommentResponse")
    UserCommentRes mapCommentResponse(User author) {

        return new UserCommentRes.Builder().userId(author.getId())
                                           .avatar(author.getAvatar())
                                           .displayName(author.getDisplayName())
                                           .level(author.getLevel())
                                           .build();
    }

    @Named("hasChildren")
    boolean hasChildren(List<Comment> children) {
        return children != null && !children.isEmpty();
    }

    @Named("mapAuthor")
    User mapAuthor(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    @Named("mapBlog")
    Blog mapBlog(Long blogId) {
        if (blogId == null) {
            return null;
        }
        return blogRepository.findById(blogId)
                             .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));
    }

    @Named("mapCommentParent")
    Comment mapCommentParent(Long parentId) {
        if (parentId == null || parentId == 0) {

            return null;
        }
        return commentRepository.findById(parentId)
                                .orElseThrow(
                                        () -> new IllegalArgumentException(
                                                "Parent comment not found with id: " + parentId));

    }
}
